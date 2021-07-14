package com.rh.i_mes_plus.service.impl.sps;
import java.util.Date;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.common.model.SysConst;
import com.rh.i_mes_plus.mapper.sps.TinTakeRecordMapper;
import com.rh.i_mes_plus.mapper.sps.TinUseRecordMapper;
import com.rh.i_mes_plus.model.pdt.PdtWmsPmMoBase;
import com.rh.i_mes_plus.model.sps.AssistantTool;
import com.rh.i_mes_plus.model.sps.TinStockInfo;
import com.rh.i_mes_plus.model.sps.TinTakeRecord;
import com.rh.i_mes_plus.model.ums.UmsUser;
import com.rh.i_mes_plus.service.pdt.IPdtWmsPmMoBaseService;
import com.rh.i_mes_plus.service.sps.IAssistantToolService;
import com.rh.i_mes_plus.service.sps.ITinStockInfoService;
import com.rh.i_mes_plus.service.sps.ITinTakeRecordService;
import com.rh.i_mes_plus.service.sps.ITinUseRecordService;
import com.rh.i_mes_plus.service.ums.IUmsUserService;
import com.rh.i_mes_plus.vo.TinTakeRecordVO;
import okhttp3.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import lombok.extern.slf4j.Slf4j;

import com.rh.i_mes_plus.model.sps.TinUseRecord;

/**
 * 红锡膏领用记录
 *
 * @author hbq
 * @date 2021-07-08 19:48:01
 */
@Slf4j
@Service
public class TinUseRecordServiceImpl extends ServiceImpl<TinUseRecordMapper, TinUseRecord> implements ITinUseRecordService {
    @Resource
    private TinUseRecordMapper tinUseRecordMapper;
    @Autowired
    private IUmsUserService umsUserService;
    @Autowired
    private ITinStockInfoService tinStockInfoService;
    @Resource
    private TinTakeRecordMapper tinTakeRecordMapper;
    @Autowired
    private IPdtWmsPmMoBaseService pdtWmsPmMoBaseService;

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
        return tinUseRecordMapper.findList(pages, params);
    }

    @Override
    public Result use(Map<String, Object> params) {
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
        if(stockInfo.getStatus()!= SysConst.TIN_STATUS.HW){
            return Result.failed("锡膏非回温状态");
        }
        int compare = DateUtil.compare(stockInfo.getExpireTime(), new Date());
        if (compare<0){
            return Result.failed("锡膏过期");
        }
        TinTakeRecordVO recordVO=tinTakeRecordMapper.getTakeStateByTinSn(tinSn);
        if (recordVO == null){
            return Result.failed("锡膏未回温");
        }
        if (recordVO.getState()!=2&&recordVO.getState()!=3){
            return Result.failed("锡膏回温未完成或常温超时，不可领用");
        }
        Integer state = recordVO.getState();
        TinUseRecord tinUseRecord = new TinUseRecord();
        tinUseRecord.setTakeId(recordVO.getId());
        tinUseRecord.setItemCode(stockInfo.getItemCode());
        tinUseRecord.setTinSn(stockInfo.getTinSn());
        tinUseRecord.setUseTime(new Date());
        tinUseRecord.setUseName(user.getUserName());
        tinUseRecord.setUseingFlag(0);
        int userState=0;
        if (state==2||state==3){
            userState=0;
        }else if (state==1){
            userState=1;
        }else if (state==4){
            userState=2;
        }
        tinUseRecord.setUseState(userState);
        save(tinUseRecord);
        //库存状态改领用
        stockInfo.setStatus(SysConst.TIN_STATUS.LY);
        tinStockInfoService.updateById(stockInfo);
        //修改回温状态
        TinTakeRecord tinTakeRecord=new TinTakeRecord();
        BeanUtil.copyProperties(recordVO,tinTakeRecord);
        tinTakeRecord.setTakeEndTime(new Date());
        tinTakeRecord.setStatus(1);
        tinTakeRecordMapper.updateById(tinTakeRecord);
        return Result.succeed("保存成功");
    }

    @Override
    public Result upTin(Map<String, Object> params) {
        String tinSn = MapUtil.getStr(params, "tinSn");
        String userAccount = MapUtil.getStr(params, "userAccount");
        String moNo = MapUtil.getStr(params, "moNo");
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
        TinUseRecord useRecord = getOne(new LambdaQueryWrapper<TinUseRecord>().eq(TinUseRecord::getTinSn, tinSn));
        PdtWmsPmMoBase moBase = pdtWmsPmMoBaseService.getOne(new LambdaQueryWrapper<PdtWmsPmMoBase>().eq(PdtWmsPmMoBase::getMoNo, moNo));
        if (moBase == null) {
            return Result.failed("制令单号不存在");
        }
        TinTakeRecordVO recordVO=tinTakeRecordMapper.getTakeStateByTinSn(tinSn);
        if (recordVO == null){
            return Result.failed("锡膏未回温");
        }
        if (recordVO.getState()==4){
            return Result.failed("锡膏常温超时，请返回仓库报废");
        }
        Integer state = recordVO.getState();
        useRecord.setMoNo(moNo);
        useRecord.setLineCode(moBase.getLineCode());

        useRecord.setOpenTime(new Date());
        useRecord.setOpenName(user.getUserName());
        useRecord.setOperator(user.getUserName());
        useRecord.setUseingTime(new Date());
        useRecord.setUseingMan(user.getUserName());
        useRecord.setUseingFlag(2);
        int userState=0;
        if (state==2||state==3){
            userState=0;
        }else if (state==1){
            userState=1;
        }else if (state==4){
            userState=2;
        }
        useRecord.setUseState(userState);
        updateById(useRecord);
        //修改库存开罐标识
        stockInfo.setIsOpen(1);
        tinStockInfoService.updateById(stockInfo);
        return Result.succeed("保存成功");
    }

}
