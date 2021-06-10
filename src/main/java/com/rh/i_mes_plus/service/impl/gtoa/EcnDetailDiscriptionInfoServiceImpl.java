package com.rh.i_mes_plus.service.impl.gtoa;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.common.model.SysConst;
import com.rh.i_mes_plus.mapper.gtoa.EcnDetailDiscriptionInfoMapper;
import com.rh.i_mes_plus.model.gtoa.EcnDetailDiscriptionInfo;
import com.rh.i_mes_plus.model.gtoa.EcnDetailInfo;
import com.rh.i_mes_plus.model.gtoa.FaiInfo;
import com.rh.i_mes_plus.service.gtoa.IEcnDetailDiscriptionInfoService;
import com.rh.i_mes_plus.service.gtoa.IEcnDetailInfoService;
import com.rh.i_mes_plus.service.gtoa.IEcnDocInfoService;
import com.rh.i_mes_plus.service.gtoa.IFaiInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author hbq
 * @date 2020-10-22 19:36:56
 */
@Slf4j
@Service
public class EcnDetailDiscriptionInfoServiceImpl extends ServiceImpl<EcnDetailDiscriptionInfoMapper, EcnDetailDiscriptionInfo> implements IEcnDetailDiscriptionInfoService {
    @Resource
    private EcnDetailDiscriptionInfoMapper ecnDetailDiscriptionInfoMapper;
    @Autowired
    @Lazy
    private IEcnDetailInfoService ecnDetailInfoService;
    @Autowired
    @Lazy
    private IEcnDocInfoService ecnDocInfoService;
    @Autowired
    private IFaiInfoService faiInfoService;
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
        return ecnDetailDiscriptionInfoMapper.findList(pages, params);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result QAcheck(Map<String, List<EcnDetailDiscriptionInfo>> map) {
        try{
            List<EcnDetailDiscriptionInfo> models = map.get("models");
            updateBatchById(models);

            Boolean isDraft = models.get(0).getIsDraft();
            if (!isDraft){
                EcnDetailDiscriptionInfo detailDiscriptionInfo = getById(models.get(0).getId());
                Long ediId = detailDiscriptionInfo.getEdiId();
                EcnDetailInfo ecnDetailInfo = ecnDetailInfoService.getById(ediId);
                String ecnNo = ecnDetailInfo.getEcnNo();
                int count = count(new QueryWrapper<EcnDetailDiscriptionInfo>()
                        .eq("edi_id", ediId));
                int hasValidCount = count(new QueryWrapper<EcnDetailDiscriptionInfo>()
                        .eq("edi_id", ediId).eq("is_draft", 0).eq("valid_flag", 1));
                /** QA检验完成
                 * 更新ecn状态
                 * 增加fai
                 * */
                if (count==hasValidCount){
                    ecnDocInfoService.updateExeStateAndTimeLog(ecnNo,SysConst.EXESTATE.QA_ACCEPTED);
                    /** 如果不需要fai*/
                    if (ecnDetailInfo.getHasFai()){
                        faiInfoService.save(new FaiInfo(ecnNo));
                    }
                }
            }
            return Result.succeed("保存成功");
        }
        catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( e,"保存失败,请联系管理员");
        }
    }

    @Override
    public Result QAcheckSuccess(Map<String, List<EcnDetailDiscriptionInfo>> map) {
        try{
            List<EcnDetailDiscriptionInfo> models = map.get("models");
            updateBatchById(models);

            Boolean isDraft = models.get(0).getIsDraft();
            if (!isDraft){
                EcnDetailDiscriptionInfo detailDiscriptionInfo = getById(models.get(0).getId());
                Long ediId = detailDiscriptionInfo.getEdiId();
                EcnDetailInfo ecnDetailInfo = ecnDetailInfoService.getById(ediId);
                String ecnNo = ecnDetailInfo.getEcnNo();
                /** QA检验完成
                 * 更新ecn状态
                 * 增加fai
                 * */
                ecnDocInfoService.updateExeStateAndTimeLog(ecnNo,SysConst.EXESTATE.QA_ACCEPTED);
                /** 如果不需要fai*/
                if (ecnDetailInfo.getHasFai()){
                    faiInfoService.save(new FaiInfo(ecnNo));
                }
            }
            return Result.succeed("保存成功");
        }
        catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( e,"保存失败,请联系管理员");
        }
    }
}
