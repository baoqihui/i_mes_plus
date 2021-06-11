package com.rh.i_mes_plus.service.impl.other;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.ProjectAndFaceDTO;
import com.rh.i_mes_plus.dto.WmsProjectDTO;
import com.rh.i_mes_plus.mapper.other.WmsProjectDetailMapper;
import com.rh.i_mes_plus.model.sms.SmsWmsOutStockPmItem;
import com.rh.i_mes_plus.model.other.WmsProjectBase;
import com.rh.i_mes_plus.model.other.WmsProjectDetail;
import com.rh.i_mes_plus.model.other.SapMesLog;
import com.rh.i_mes_plus.service.sms.ISmsWmsOutStockPmItemService;
import com.rh.i_mes_plus.service.other.IWmsProjectBaseService;
import com.rh.i_mes_plus.service.other.IWmsProjectDetailService;
import com.rh.i_mes_plus.service.other.ISapMesLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工单明细表
 *
 * @author hbq
 * @date 2020-10-30 11:19:39
 */
@Slf4j
@Service
public class WmsProjectDetailServiceImpl extends ServiceImpl<WmsProjectDetailMapper, WmsProjectDetail> implements IWmsProjectDetailService {
    @Resource
    private WmsProjectDetailMapper wmsProjectDetailMapper;
    @Autowired
    private IWmsProjectBaseService wmsProjectBaseService;
    @Autowired
    private ISapMesLogService sapMesLogService;
    @Autowired
    private ISmsWmsOutStockPmItemService smsWmsOutStockPmItemService;
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
        return wmsProjectDetailMapper.findList(pages, params);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result saveAll(List<WmsProjectDTO> wmsProjectDTOS) {

            Map<String,Object> resultMap=new HashMap();
            List<String> docS=new ArrayList<>();
            List<String> detS=new ArrayList<>();
            for (WmsProjectDTO wmsProjectDTO : wmsProjectDTOS) {
                WmsProjectBase wmsProjectBase = wmsProjectDTO.getWmsProjectBase();
                wmsProjectBase.setProjectStatus("1");
                List<WmsProjectDetail> wmsProjectDetails = wmsProjectDTO.getWmsProjectDetails();

                WmsProjectBase existWmsProjectBase = wmsProjectBaseService.getOne(new QueryWrapper<WmsProjectBase>()
                        .eq("project_id", wmsProjectBase.getProjectId())
                );

                boolean docSuccessFlag;
                if (existWmsProjectBase != null) {
                    docSuccessFlag = wmsProjectBaseService.update(wmsProjectBase,new QueryWrapper<WmsProjectBase>()
                            .eq("project_id", wmsProjectBase.getProjectId())
                    );
                }else{
                    docSuccessFlag = wmsProjectBaseService.save(wmsProjectBase);
                }
                if (docSuccessFlag){
                    docS.add(wmsProjectBase.getProjectId());
                    boolean finalFlag=true;
                    for (WmsProjectDetail wmsProjectDetail : wmsProjectDetails) {
                        System.out.println(wmsProjectDetail);
                        boolean detSuccessFlag;
                        WmsProjectDetail existWmsProjectDetail = getOne(new QueryWrapper<WmsProjectDetail>()
                                .eq("project_id", wmsProjectDetail.getProjectId())
                                .eq("item_code", wmsProjectDetail.getItemCode())
                        );
                        if (existWmsProjectDetail != null) {
                            detSuccessFlag=update(wmsProjectDetail,new QueryWrapper<WmsProjectDetail>()
                                    .eq("project_id", wmsProjectDetail.getProjectId())
                                    .eq("item_code", wmsProjectDetail.getItemCode())
                            );
                        }else {
                            detSuccessFlag=save(wmsProjectDetail);
                        }
                        if (!detSuccessFlag){
                            //如果存在失败，将docSuccessFlag标记为失败并记录到log
                            SapMesLog sapMesLog = new SapMesLog(
                                    "det错误，project_id="+wmsProjectBase.getProjectId(),
                                    "/wms/wmsProjectDetail/saveAll",
                                    1,
                                    "item_code="+wmsProjectDetail.getItemCode()
                            );
                            //直接存入日志表
                            sapMesLogService.save(sapMesLog);
                            finalFlag=false;
                        }
                    }
                    if (finalFlag){
                        detS.add(wmsProjectBase.getProjectId());
                    }
                }else{
                    SapMesLog sapMesLog = new SapMesLog(
                            "doc错误，project_id="+wmsProjectBase.getProjectId(),
                            "/sms/wmsProjectDetail/saveAll",
                            1,
                            "doc直接错误，未添加det");
                    //直接存入日志表
                    sapMesLogService.save(sapMesLog);
                }
                resultMap.put("docS",docS);
                resultMap.put("detS",detS);
            }
        try{    return Result.succeed(resultMap,"保存成功");
        }
        catch (Exception e){
            // 事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failed( "添加失败");
        }
    }

    @Override
    public Result getDetailByProjectIds(Map<String, List<ProjectAndFaceDTO>> params) {
        List<ProjectAndFaceDTO> models = params.get("models");
        List<Map<String,Object>> details=new ArrayList<>();
        for (ProjectAndFaceDTO model : models) {
            System.out.println(model);
            List<Map<String,Object>> maps=wmsProjectDetailMapper.getDetailByProjectIdsAndFace(model);
            for (Map<String, Object> map : maps) {
                /**
                 * 同一工单，同料号，同加工面仅备料一次
                 * */
                String projectId = MapUtil.getStr(map,"projectId");
                String itemCode = MapUtil.getStr(map,"itemCode");
                String scFlag = MapUtil.getStr(map,"scFlag");
                System.out.println(map);
                int count = smsWmsOutStockPmItemService.count(new QueryWrapper<SmsWmsOutStockPmItem>()
                        .eq("project_id", projectId)
                        .eq("item_code", itemCode)
                        .eq("sc_flag", scFlag)
                );
                System.out.println(count);
                if (count<=0){
                    details.add(map);
                }
            }
        }
        if (details.size()<=0){
            return Result.failed("同一工单，同料号，同加工面仅备料一次");
        }
        return Result.succeed(details,"查询成功");
    }
}
