package com.rh.i_mes_plus.service.impl.sps;
import java.util.Date;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.common.model.SysConst;
import com.rh.i_mes_plus.mapper.sps.TinTakeRecordMapper;
import com.rh.i_mes_plus.model.sps.AssistantTool;
import com.rh.i_mes_plus.model.sps.TinStockInfo;
import com.rh.i_mes_plus.model.ums.UmsUser;
import com.rh.i_mes_plus.service.sps.IAssistantToolService;
import com.rh.i_mes_plus.service.sps.ITinStockInfoService;
import com.rh.i_mes_plus.service.sps.ITinTakeRecordService;
import com.rh.i_mes_plus.service.ums.IUmsUserService;
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
        if(stockInfo.getStatus()!=0){
            return Result.failed("锡膏非在库状态");
        }
        String itemCode = stockInfo.getItemCode();
        AssistantTool tool = assistantToolService.getOne(new LambdaQueryWrapper<AssistantTool>()
                .eq(AssistantTool::getItemCode,itemCode));
        if (tool == null) {
            return Result.failed("物料不存在");
        }
        Integer backTime = tool.getBackTime();
        //添加回温日志
        TinTakeRecord tinTakeRecord = new TinTakeRecord();
        tinTakeRecord.setTinSn(tinSn);
        tinTakeRecord.setTakeStartTime(new Date());
        tinTakeRecord.setTakeStartName(user.getUserName());
        tinTakeRecord.setTakeEndTime(DateUtil.offsetMinute(new Date(),backTime));
        tinTakeRecord.setItemCode(stockInfo.getItemCode());
        save(tinTakeRecord);
        //更改库存状态
        stockInfo.setStatus(SysConst.TIN_STATUS.HW);
        tinStockInfoService.updateById(stockInfo);
        return Result.succeed("保存成功");
    }

}
