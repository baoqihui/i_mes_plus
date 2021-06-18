package com.rh.i_mes_plus.service.impl.pdt;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.mapper.pdt.PdtWmsOutStockListMapper;
import com.rh.i_mes_plus.model.other.EccMaterialDetail;
import com.rh.i_mes_plus.model.other.EccSendInfo;
import com.rh.i_mes_plus.model.pdt.PdtWmsOutStockList;
import com.rh.i_mes_plus.service.pdt.IPdtWmsOutStockListService;
import com.rh.i_mes_plus.service.other.IEccMaterialDetailService;
import com.rh.i_mes_plus.service.other.IEccSendInfoService;
import com.rh.i_mes_plus.vo.EccVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 成品出库明细表
 *
 * @author hbq
 * @date 2021-01-06 15:22:23
 */
@Slf4j
@Service
public class PdtWmsOutStockListServiceImpl extends ServiceImpl<PdtWmsOutStockListMapper, PdtWmsOutStockList> implements IPdtWmsOutStockListService {
    @Resource
    private PdtWmsOutStockListMapper pdtWmsOutStockListMapper;
    @Autowired
    private IEccMaterialDetailService eccMaterialDetailService;
    @Autowired
    private IEccSendInfoService eccSendInfoService;
    @Value("${zhaoIpAndPort}")
    private String zhaoIpAndPort;
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
        return pdtWmsOutStockListMapper.findList(pages, params);
    }

    @Override
    public Result ecc(String docNo) {
        List<EccVO> eccVOS= pdtWmsOutStockListMapper.ecc(docNo);
        if (eccVOS == null||eccVOS.size()<=0) {
            return Result.failed("无库存");
        }
        int line=1;
        for (EccVO eccVO : eccVOS) {
            String moListResult= HttpUtil.get(zhaoIpAndPort+"/Api/wms/bom/mo/list?batch="+eccVO.getBatch());
            List<String> moList = JSONArray.parseArray(moListResult, String.class);
            for (String mo : moList) {
                Long qty = eccVO.getQty();
                List<EccMaterialDetail> details = eccMaterialDetailService.list(new QueryWrapper<EccMaterialDetail>()
                        .eq("wo_no", mo));
                /** 1.ECC生产BOM表中不存在该工单号,需要先同步表单*/
                if (details == null||details.size()<=0) {
                    String result= HttpUtil.get(zhaoIpAndPort+"/Api/wms/bom/detail/mo?batch="+eccVO.getBatch());
                    List<EccMaterialDetail> eccMaterialDetails = JSONArray.parseArray(result, EccMaterialDetail.class);
                    eccMaterialDetailService.saveBatch(eccMaterialDetails);
                }
                //如果sub_group为空，无替代料，直接修改数据
                List<EccMaterialDetail> existDetails = eccMaterialDetailService.list(new QueryWrapper<EccMaterialDetail>()
                        .eq("wo_no", mo).last("and sub_group=''"));
                for (EccMaterialDetail existDetail : existDetails) {
                    /** 2.根据工单号查到所有bom列表，使用数量乘以单板用量加到已收*/
                    Long panelQty = existDetail.getPanelQty();
                    Long sendQty = qty * panelQty;
                    existDetail.setHasSendQty(existDetail.getHasSendQty()+sendQty);
                    eccMaterialDetailService.updateById(existDetail);

                    /** 3.添加扣料数据表*/
                    EccSendInfo eccSendInfo = new EccSendInfo();
                    eccSendInfo.setRequestNo(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
                    eccSendInfo.setBatchCount(qty);
                    eccSendInfo.setBatchcode(eccVO.getBatch());
                    eccSendInfo.setMaterialcode(eccVO.getModelCode());
                    eccSendInfo.setShipDate(new Date());
                    eccSendInfo.setCount(qty);
                    eccSendInfo.setComponentcode(existDetail.getMaterialPn());
                    eccSendInfo.setComponentcount(sendQty);
                    eccSendInfo.setLinecode(line);
                    eccSendInfo.setOrdercode("");
                    eccSendInfo.setWoNo(existDetail.getWoNo());
                    eccSendInfo.setDocId(eccVO.getDocNo());
                    eccSendInfoService.save(eccSendInfo);
                }
                //找到最大的组，进行遍历
                Integer maxSub=pdtWmsOutStockListMapper.getMaxSubGroup(mo);
                if (maxSub!=null){
                    for (int i=1;i<=maxSub;i++){
                        //拿到第i组数据

                        List<EccMaterialDetail> detailList=pdtWmsOutStockListMapper.getMaterialList(mo,i);
                        if (detailList!=null&&detailList.size()>0){
                            //本组需发数量
                            Long needSendQty=detailList.get(0).getPanelQty()*qty;
                            if (needSendQty==0L){
                                break;
                            }
                            for (EccMaterialDetail eccMaterialDetail : detailList) {
                                Long totalQty = eccMaterialDetail.getTotalQty();        //总数
                                Long hasSendQty = eccMaterialDetail.getHasSendQty();    //已发数量

                                Long canSendQty = totalQty - hasSendQty;                   //可发数量
                                if (0L==canSendQty){
                                    continue;
                                }
                                //如果本次需发货数量小于等于可发数量，直接发送并结束替代组循环
                                if (needSendQty<=canSendQty){
                                    eccMaterialDetail.setHasSendQty(hasSendQty+needSendQty);
                                    eccMaterialDetailService.updateById(eccMaterialDetail);

                                    EccSendInfo eccSendInfo = new EccSendInfo();
                                    eccSendInfo.setRequestNo(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
                                    eccSendInfo.setBatchCount(qty);
                                    eccSendInfo.setBatchcode(eccVO.getBatch());
                                    eccSendInfo.setMaterialcode(eccVO.getModelCode());
                                    eccSendInfo.setShipDate(new Date());
                                    eccSendInfo.setCount(qty);
                                    eccSendInfo.setComponentcode(eccMaterialDetail.getMaterialPn());
                                    eccSendInfo.setComponentcount(needSendQty);
                                    eccSendInfo.setLinecode(line);
                                    eccSendInfo.setOrdercode("");
                                    eccSendInfo.setWoNo(eccMaterialDetail.getWoNo());
                                    eccSendInfo.setDocId(eccVO.getDocNo());
                                    eccSendInfoService.save(eccSendInfo);
                                    needSendQty=0L;
                                }
                                //如果本次发货数量大于可发数量
                                if (needSendQty>canSendQty){
                                    eccMaterialDetail.setHasSendQty(hasSendQty+canSendQty);
                                    eccMaterialDetailService.updateById(eccMaterialDetail);

                                    EccSendInfo eccSendInfo = new EccSendInfo();
                                    eccSendInfo.setRequestNo(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
                                    eccSendInfo.setBatchCount(qty);
                                    eccSendInfo.setBatchcode(eccVO.getBatch());
                                    eccSendInfo.setMaterialcode(eccVO.getModelCode());
                                    eccSendInfo.setShipDate(new Date());
                                    eccSendInfo.setCount(qty);
                                    eccSendInfo.setComponentcode(eccMaterialDetail.getMaterialPn());
                                    eccSendInfo.setComponentcount(canSendQty);
                                    eccSendInfo.setLinecode(line);
                                    eccSendInfo.setOrdercode("");
                                    eccSendInfo.setWoNo(eccMaterialDetail.getWoNo());
                                    eccSendInfo.setDocId(eccVO.getDocNo());
                                    eccSendInfoService.save(eccSendInfo);
                                    needSendQty=needSendQty-canSendQty;
                                }
                            }

                        }
                    }
                }
            }
            line++;
        }

        return Result.succeed("成功");
    }

    @Override
    public Page<Map> listAll(Map<String, Object> params) {
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<Map> pages = new Page<>(pageNum, pageSize);
        return pdtWmsOutStockListMapper.listAll(pages, params);
    }

    @Override
    public Page<Map> listAllCollect(Map<String, Object> params) {
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<Map> pages = new Page<>(pageNum, pageSize);
        return pdtWmsOutStockListMapper.listAllCollect(pages, params);
    }

    @Override
    public Page<Map> listAllCollectByBoxNo(Map<String, Object> params) {
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<Map> pages = new Page<>(pageNum, pageSize);
        return pdtWmsOutStockListMapper.listAllCollectByBoxNo(pages, params);
    }
}
