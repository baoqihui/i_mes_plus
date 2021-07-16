package com.rh.i_mes_plus.service.impl.sps;
import java.util.Date;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.common.model.SysConst;
import com.rh.i_mes_plus.mapper.sps.TinTakeRecordMapper;
import com.rh.i_mes_plus.model.sps.AssistantTool;
import com.rh.i_mes_plus.model.sps.TinLog;
import com.rh.i_mes_plus.model.sps.TinStockInfo;
import com.rh.i_mes_plus.model.ums.UmsUser;
import com.rh.i_mes_plus.service.sps.IAssistantToolService;
import com.rh.i_mes_plus.service.sps.ITinLogService;
import com.rh.i_mes_plus.service.sps.ITinStockInfoService;
import com.rh.i_mes_plus.service.sps.ITinTakeRecordService;
import com.rh.i_mes_plus.service.ums.IUmsUserService;
import com.rh.i_mes_plus.vo.TinTakeRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import lombok.extern.slf4j.Slf4j;

import com.rh.i_mes_plus.model.sps.TinTakeRecord;

/**
 * 
 *
 * @author hbq
 * @date 2021-07-08 19:48:01
 */
@Slf4j
@Service
public class TinTakeRecordServiceImpl extends ServiceImpl<TinTakeRecordMapper, TinTakeRecord> implements ITinTakeRecordService {
    @Resource
    private TinTakeRecordMapper tinTakeRecordMapper;
    @Autowired
    private IUmsUserService umsUserService;
    @Autowired
    private IAssistantToolService assistantToolService;
    @Autowired
    private ITinStockInfoService tinStockInfoService;
    @Autowired
    private ITinLogService tinLogService;
    /**
     * 列表
     * @param params
     * @return
     */
    public Page<TinTakeRecordVO> findList(Map<String, Object> params){
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<TinTakeRecordVO> pages = new Page<>(pageNum, pageSize);
        return tinTakeRecordMapper.findList(pages, params);
    }

    @Override
    public Result take(Map<String, Object> params) {
        String tinSn = MapUtil.getStr(params, "tinSn");
        String userAccount = MapUtil.getStr(params, "userAccount");
        UmsUser user = umsUserService.getOne(new LambdaQueryWrapper<UmsUser>()
                .eq(UmsUser::getUserAccount, userAccount)
        );
        TinStockInfo stockInfo = tinStockInfoService.getOne(new LambdaQueryWrapper<TinStockInfo>()
                .eq(TinStockInfo::getTinSn, tinSn));
        if (user == null) {
            return Result.failed("用户不存在");
        }
        if (stockInfo == null) {
            return Result.failed("锡膏不存在");
        }
        if(stockInfo.getStatus()!=SysConst.TIN_STATUS.ZK&&stockInfo.getStatus()!=SysConst.TIN_STATUS.TK){
            return Result.failed("锡膏非在库或退库状态");
        }
        int compare = DateUtil.compare(stockInfo.getExpireTime(), new Date());
        if (compare<0){
            return Result.failed("锡膏过期");
        }
        String itemCode = stockInfo.getItemCode();
        AssistantTool tool = assistantToolService.getOne(new LambdaQueryWrapper<AssistantTool>()
                .eq(AssistantTool::getItemCode,itemCode));
        if (tool == null) {
            return Result.failed("物料不存在");
        }
        //添加回温日志
        TinTakeRecord tinTakeRecord = new TinTakeRecord();
        tinTakeRecord.setTinSn(tinSn);
        tinTakeRecord.setTakeStartTime(new Date());
        tinTakeRecord.setTakeStartName(user.getUserName());
        tinTakeRecord.setItemCode(stockInfo.getItemCode());
        save(tinTakeRecord);
        //更改库存状态
        stockInfo.setStatus(SysConst.TIN_STATUS.HW);
        tinStockInfoService.updateById(stockInfo);
        //添加日志
        TinLog tinLog=TinLog.builder()
                .tinSn(stockInfo.getTinSn())
                .itemCode(stockInfo.getItemCode())
                .manufactureDate(stockInfo.getManufactureDate())
                .lotNo(stockInfo.getLotNo())
                .content("回温 "+user.getUserName()+" 在"+DateUtil.now()+"操作")
                .build();
        tinLogService.save(tinLog);
        return Result.succeed("保存成功");
    }

    @Override
    public Result cancelTake(Map<String, Object> params) {
        String tinSn = MapUtil.getStr(params, "tinSn");
        String userAccount = MapUtil.getStr(params, "userAccount");
        UmsUser user = umsUserService.getOne(new LambdaQueryWrapper<UmsUser>()
                .eq(UmsUser::getUserAccount, userAccount)
        );
        TinStockInfo stockInfo = tinStockInfoService.getOne(new LambdaQueryWrapper<TinStockInfo>()
                .eq(TinStockInfo::getTinSn, tinSn));
        if (user == null) {
            return Result.failed("用户不存在");
        }
        if (stockInfo == null) {
            return Result.failed("锡膏不存在");
        }
        if(stockInfo.getStatus()!=SysConst.TIN_STATUS.HW){
            return Result.failed("锡膏非回温状态");
        }
        TinTakeRecord takeRecord = getOne(new LambdaQueryWrapper<TinTakeRecord>().eq(TinTakeRecord::getTinSn, tinSn));
        if (takeRecord.getStatus()==1){
            return Result.failed("回温已完成，无法取消");
        }
        //删除回温记录
        removeById(takeRecord);
        stockInfo.setStatus(SysConst.TIN_STATUS.ZK);
        //修改库存状态
        tinStockInfoService.updateById(stockInfo);
        //添加日志
        TinLog tinLog=TinLog.builder()
                .tinSn(stockInfo.getTinSn())
                .itemCode(stockInfo.getItemCode())
                .manufactureDate(stockInfo.getManufactureDate())
                .lotNo(stockInfo.getLotNo())
                .content("取消回温 "+user.getUserName()+" 在"+DateUtil.now()+"操作 回温时长为："+DateUtil.between(takeRecord.getTakeStartTime(),new Date(),DateUnit.HOUR)+"(h)")
                .build();
        tinLogService.save(tinLog);
        return Result.succeed("保存成功");
    }
}
