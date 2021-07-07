package com.rh.i_mes_plus.service.impl.pdt;
import java.util.Date;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.mapper.pdt.PdtWmsPmMoBaseMapper;
import com.rh.i_mes_plus.model.gtoa.TaskExecuteInfo;
import com.rh.i_mes_plus.model.other.WmsProjectDetail;
import com.rh.i_mes_plus.model.pdt.*;
import com.rh.i_mes_plus.service.other.IWmsProjectDetailService;
import com.rh.i_mes_plus.service.pdt.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 指令单号表
 *
 * @author hbq
 * @date 2020-12-28 13:28:45
 */
@Slf4j
@Service
public class PdtWmsPmMoBaseServiceImpl extends ServiceImpl<PdtWmsPmMoBaseMapper, PdtWmsPmMoBase> implements IPdtWmsPmMoBaseService {
    @Resource
    private PdtWmsPmMoBaseMapper pdtWmsPmMoBaseMapper;
    @Autowired
    private IWmsProjectDetailService wmsProjectDetailService;
    @Autowired
    private IPdtFeedingStationDetailService pdtFeedingStationDetailService;
    @Autowired
    private IPdtFeedingStationService pdtFeedingStationService;
    @Autowired
    private IPdtMoFeedingStationDetailService pdtMoFeedingStationDetailService;
    @Autowired
    private IPdtReplaceItemService pdtReplaceItemService;
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
        return pdtWmsPmMoBaseMapper.findList(pages, params);
    }

    @Override
    public Map selDetailByMoNo(String moNo) {
        return pdtWmsPmMoBaseMapper.selDetailByMoNo(moNo);
    }

    @Override
    public Result saveNew(PdtWmsPmMoBase pdtWmsPmMoBase) {
        if (pdtWmsPmMoBase.getId()==null){
            String projectId = pdtWmsPmMoBase.getProjectId();
            String scFlag = pdtWmsPmMoBase.getScFlag();
            String fsSn = pdtWmsPmMoBase.getFsSn();
            String modelCode = pdtWmsPmMoBase.getModelCode();
            PdtFeedingStation feedingStation = pdtFeedingStationService.getOne(new LambdaQueryWrapper<PdtFeedingStation>().eq(PdtFeedingStation::getFsSn, fsSn));
            List<WmsProjectDetail> projectDetails = wmsProjectDetailService.list(new LambdaQueryWrapper<WmsProjectDetail>()
                    .eq(WmsProjectDetail::getProjectId, projectId)
                    .eq(WmsProjectDetail::getScFlag, scFlag));
            List<PdtFeedingStationDetail> stationDetails = pdtFeedingStationDetailService.list(new LambdaQueryWrapper<PdtFeedingStationDetail>()
                    .eq(PdtFeedingStationDetail::getFsSn, fsSn));
            PdtMoFeedingStationDetail moFeedingStationDetail = new PdtMoFeedingStationDetail();
            moFeedingStationDetail.setMoNo(pdtWmsPmMoBase.getMoNo());
            moFeedingStationDetail.setModelCode(modelCode);
            moFeedingStationDetail.setPasterType(feedingStation.getPasterType());

            for (PdtFeedingStationDetail stationDetail : stationDetails) {
                String itemCode = stationDetail.getItemCode();
                List<WmsProjectDetail> newProjectDetails=projectDetails.stream().filter(u->u.getItemCode().contains(itemCode)).collect(Collectors.toList());
                if (newProjectDetails.size()==1){
                    WmsProjectDetail projectDetail = newProjectDetails.get(0);
                    //pdt_feeding_station_detail.item_code = wms_project_detail.item_code
                    String subItemCode = projectDetail.getSubItemCode();
                    if (StrUtil.isNotBlank(subItemCode)){
                        //寻找替代料存入replace字段
                        String[] split = subItemCode.split("-");
                        String suffix = split[split.length - 1];
                        String prefix = StrUtil.removeSuffix(subItemCode, suffix);
                        List<WmsProjectDetail> replaceProjectDetail = wmsProjectDetailService.list(new LambdaQueryWrapper<WmsProjectDetail>()
                                .eq(WmsProjectDetail::getProjectId, projectId)
                                .like(WmsProjectDetail::getSubItemCode, prefix)
                                .ne(WmsProjectDetail::getItemCode, itemCode)
                        );
                        String replaceItemCode = replaceProjectDetail.stream().map(WmsProjectDetail::getItemCode).collect(Collectors.joining(","));
                        moFeedingStationDetail.setReplaceItemCode(replaceItemCode);
                    }else {
                        moFeedingStationDetail.setReplaceItemCode("");
                    }
                    moFeedingStationDetail.setItemCode(itemCode);
                    moFeedingStationDetail.setFeedingPointSn(stationDetail.getFeedingPointSn());
                    moFeedingStationDetail.setPosition(stationDetail.getPosition());
                    moFeedingStationDetail.setQty(stationDetail.getQty());
                    moFeedingStationDetail.setChannel(stationDetail.getChannel());
                    moFeedingStationDetail.setStatus(0);
                    pdtMoFeedingStationDetailService.save(moFeedingStationDetail);
                }else {
                    List<PdtReplaceItem> placeItems = pdtReplaceItemService.getGroupItemListByItemCode(itemCode, modelCode, 1);
                    for (PdtReplaceItem placeItem : placeItems) {
                        String replaceItemCode2 = placeItem.getItemCode();
                        for (WmsProjectDetail detail : projectDetails) {
                            if (replaceItemCode2.equals(detail.getItemCode())){
                                String subItemCode = detail.getSubItemCode();

                                if (StrUtil.isNotBlank(subItemCode)){
                                    //寻找替代料存入replace字段
                                    String[] split = subItemCode.split("-");
                                    String suffix = split[split.length - 1];
                                    String prefix = StrUtil.removeSuffix(subItemCode, suffix);
                                    List<WmsProjectDetail> replaceProjectDetail = wmsProjectDetailService.list(new LambdaQueryWrapper<WmsProjectDetail>()
                                            .eq(WmsProjectDetail::getProjectId, projectId)
                                            .like(WmsProjectDetail::getSubItemCode, prefix)
                                            .ne(WmsProjectDetail::getItemCode, replaceItemCode2)
                                    );
                                    String replaceItemCode = replaceProjectDetail.stream().map(WmsProjectDetail::getItemCode).collect(Collectors.joining(","));
                                    moFeedingStationDetail.setReplaceItemCode(replaceItemCode);
                                }else {
                                    moFeedingStationDetail.setReplaceItemCode("");
                                }
                                moFeedingStationDetail.setItemCode(replaceItemCode2);
                                moFeedingStationDetail.setFeedingPointSn(stationDetail.getFeedingPointSn());
                                moFeedingStationDetail.setPosition(stationDetail.getPosition());
                                moFeedingStationDetail.setQty(stationDetail.getQty());
                                moFeedingStationDetail.setChannel(stationDetail.getChannel());
                                moFeedingStationDetail.setStatus(0);
                                pdtMoFeedingStationDetailService.save(moFeedingStationDetail);
                            }
                        }
                    }
                }
            }
        }
        save(pdtWmsPmMoBase);
        return Result.succeed("保存成功");
    }
    /**
     * 获取新的串号
     * @return 当前串号的下一个串号
     */
    @Override
    public String getNewNoByProjectId(String projectId) {
        //生成新的串号
        String prefix=projectId;
        System.out.println(projectId);
        String maxNo=prefix+"01";
        PdtWmsPmMoBase one = getOne(new QueryWrapper<PdtWmsPmMoBase>()
                .last("where mo_no like '" + prefix + "%' order by mo_no desc limit 1"));
        if (one != null) {
            Long aLong = Convert.toLong(StrUtil.removePrefix(one.getMoNo(), prefix));
            maxNo=prefix+String.format("%2d",aLong+1).replace(" ","0");
        }
        return maxNo;
    }

}