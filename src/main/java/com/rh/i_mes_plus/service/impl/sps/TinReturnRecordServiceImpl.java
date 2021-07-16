package com.rh.i_mes_plus.service.impl.sps;
import java.util.Date;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.common.model.SysConst;
import com.rh.i_mes_plus.mapper.sps.TinReturnRecordMapper;
import com.rh.i_mes_plus.model.sps.*;
import com.rh.i_mes_plus.model.ums.UmsUser;
import com.rh.i_mes_plus.service.sps.*;
import com.rh.i_mes_plus.service.ums.IUmsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 红锡膏退仓记录
 *
 * @author hbq
 * @date 2021-07-08 19:48:01
 */
@Slf4j
@Service
public class TinReturnRecordServiceImpl extends ServiceImpl<TinReturnRecordMapper, TinReturnRecord> implements ITinReturnRecordService {
    @Resource
    private TinReturnRecordMapper tinReturnRecordMapper;
    @Resource
    private ITinUseRecordService tinUseRecordService;
    @Autowired
    private IUmsUserService umsUserService;
    @Autowired
    private ITinStockInfoService tinStockInfoService;
    @Autowired
    private ITinTakeRecordService tinTakeRecordService;
    @Autowired
    private ITinLogService tinLogService;
    /**
     * 列表
     * @param params
     * @return
     */
    public Page<Map> findList(Map<String, Object> params){
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<Map> pages = new Page<>(pageNum, pageSize);
        return tinReturnRecordMapper.findList(pages, params);
    }

    @Override
    public Result returnRecord(Map<String, Object> params) {
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
        if(stockInfo.getStatus()!= SysConst.TIN_STATUS.LY){
            return Result.failed("锡膏非领用状态");
        }
        TinUseRecord useRecord = tinUseRecordService.getOne(new LambdaQueryWrapper<TinUseRecord>().eq(TinUseRecord::getTinSn, tinSn));
        if (useRecord == null) {
            return Result.failed("锡膏无领用记录");
        }
        //添加退料记录
        TinReturnRecord returnRecord = new TinReturnRecord();
        returnRecord.setUseId(useRecord.getTakeId());
        returnRecord.setTinSn(tinSn);
        returnRecord.setReturnTime(new Date());
        returnRecord.setReturnName(user.getUserName());

        Integer useingFlag = useRecord.getUseingFlag();
        if (useingFlag==0){
            returnRecord.setState(1);
        }
        if (useingFlag==1){
            returnRecord.setState(2);
        }
        save(returnRecord);
        //删除领用记录和回温记录
        tinUseRecordService.remove(new LambdaQueryWrapper<TinUseRecord>().eq(TinUseRecord::getTinSn,tinSn));
        tinTakeRecordService.remove(new LambdaQueryWrapper<TinTakeRecord>().eq(TinTakeRecord::getTinSn,tinSn));
        //修改库存
        stockInfo.setStatus(SysConst.TIN_STATUS.TK);
        tinStockInfoService.updateById(stockInfo);

        //添加日志
        TinLog tinLog=TinLog.builder()
                .tinSn(stockInfo.getTinSn())
                .itemCode(stockInfo.getItemCode())
                .manufactureDate(stockInfo.getManufactureDate())
                .lotNo(stockInfo.getLotNo())
                .content("操作人："+user.getUserName()+" 在"+ DateUtil.now() +" 退库 操作")
                .build();
        tinLogService.save(tinLog);
        return Result.succeed("保存成功");
    }
}
