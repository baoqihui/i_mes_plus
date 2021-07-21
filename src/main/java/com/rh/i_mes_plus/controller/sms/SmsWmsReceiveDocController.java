package com.rh.i_mes_plus.controller.sms;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.SmsWmsReceiveAllDTO;
import com.rh.i_mes_plus.model.other.WmsProjectBase;
import com.rh.i_mes_plus.model.sms.SmsWmsPoDet;
import com.rh.i_mes_plus.model.sms.SmsWmsReceiveDetail;
import com.rh.i_mes_plus.model.sms.SmsWmsReceiveDoc;
import com.rh.i_mes_plus.model.ums.UmsItemSap;
import com.rh.i_mes_plus.service.other.IWmsProjectBaseService;
import com.rh.i_mes_plus.service.sms.ISmsWmsBarcodeInfoService;
import com.rh.i_mes_plus.service.sms.ISmsWmsPoDetService;
import com.rh.i_mes_plus.service.sms.ISmsWmsReceiveDetailService;
import com.rh.i_mes_plus.service.sms.ISmsWmsReceiveDocService;
import com.rh.i_mes_plus.service.ums.IUmsDepaService;
import com.rh.i_mes_plus.service.ums.IUmsItemSapService;
import com.rh.i_mes_plus.util.EasyPoiExcelUtil;
import com.rh.i_mes_plus.vo.SmsWmsReceiveDetailVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 采购入库单表
 *
 * @author hqb
 * @date 2020-10-07 15:25:51
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "入库单表")
@RequestMapping("sms")
public class SmsWmsReceiveDocController {
    @Autowired
    private ISmsWmsReceiveDocService smsWmsReceiveDocService;
    @Autowired
    private ISmsWmsReceiveDetailService smsWmsReceiveDetailService;
    @Autowired
    private ISmsWmsPoDetService smsWmsPoDetService;
    @Autowired
    private IUmsDepaService umsDepaService;
    @Autowired
    private IWmsProjectBaseService wmsProjectBaseService;
    @Autowired
    private IUmsItemSapService umsItemSapService;
    @Autowired
    private ISmsWmsBarcodeInfoService smsWmsBarcodeInfoService;

    @ApiOperation(value = "通过类型号获取入库单号")
    @PostMapping("/mobile/getReceiveList")
    public List<String> getReceiveList(@RequestBody Map<String, Object> params) {
        List<String> receiveList=new ArrayList<>();
        receiveList.add("ALL");
        receiveList.addAll(smsWmsReceiveDocService.getReceiveList(params));
        return receiveList;
    }
    /**
     * 通过sn查询条码详情
     */
    @ApiOperation(value = "通过sn查询条码详情")
    @PostMapping("/smsWmsReceiveDoc/getInfoBySn")
    public JSONObject getInfoBySn(@RequestBody Map<String, Object> params) {
        return smsWmsReceiveDocService.getInfoBySn(params);
    }
    @ApiOperation(value = "查询即将生成的入库单号")
    @PostMapping("/smsWmsReceiveDoc/getDocNum")
    public Result getDocNum(@RequestBody Map<String,Object> map) {
        return smsWmsReceiveDocService.getDocNum(map);
    }
    
    @ApiOperation(value = "查询即将生成的采购入库单号")
    @PostMapping("/smsWmsReceiveDoc/getNewDocNum")
    public Result getNewDocNum() {
        String prefix = DateUtil.format(new Date(), "yyyyMM");
        String newWrDocNum = smsWmsReceiveDocService.getNewDocNum(prefix);
        return Result.succeed(newWrDocNum,"查询成功");
    }
    @ApiOperation(value = "入库单关结")
    @PostMapping("/smsWmsReceiveDoc/close")
    public Result close(@RequestBody Map<String,Object> map) {
        return smsWmsReceiveDocService.close(map);
    }
    @ApiOperation(value = "其他入库库单新增保存")
    @PostMapping("/smsWmsReceiveDoc/otherWarehouseSave")
    public Result otherWarehouseSave(@RequestBody SmsWmsReceiveAllDTO smsWmsReceiveAllDTO) {
        return smsWmsReceiveDocService.otherWarehouseSave(smsWmsReceiveAllDTO);
    }
    @ApiOperation(value = "其他入库库单修改保存")
    @PostMapping("/smsWmsReceiveDoc/otherWarehouseUpdate")
    public Result otherWarehouseUpdate(@RequestBody SmsWmsReceiveAllDTO smsWmsReceiveAllDTO) {
        return smsWmsReceiveDocService.otherWarehouseUpdate(smsWmsReceiveAllDTO);
    }
    
    @ApiOperation(value = "供货（采购入库）单新增保存")
    @PostMapping("/smsWmsReceiveDoc/saveAll")
    public Result saveAll(@RequestBody SmsWmsReceiveAllDTO smsWmsReceiveAllDTO) {
        return smsWmsReceiveDocService.saveAll(smsWmsReceiveAllDTO);
    }

    @ApiOperation(value = "供货（采购入库）单修改")
    @PostMapping("/smsWmsReceiveDoc/updateAll")
    public Result updateAll(@RequestBody SmsWmsReceiveAllDTO smsWmsReceiveAllDTO) {
        return smsWmsReceiveDocService.updateAll(smsWmsReceiveAllDTO);
    }
    @ApiOperation(value = "PDA扫码")
    @PostMapping("/mobile/pdaReceive")
    public Result pdaReceive(@RequestBody Map<String,Object> map) {
        return smsWmsReceiveDocService.pdaReceive(map);
    }

    @ApiOperation(value = "PDA扫码取消")
    @PostMapping("/mobile/pdaReceiveCancel")
    public Result pdaReceiveCancel(@RequestBody Map<String,Object> map) {
        return smsWmsReceiveDocService.pdaReceiveCancel(map);
    }

    @ApiOperation(value = "根据采购入库单号查询入库详情")
    @PostMapping("/smsWmsReceiveDoc/selAll")
    public Result selAll(@RequestBody Map<String,Object> map) {
        String wrDocNum = MapUtil.getStr(map, "wrDocNum");
        List<SmsWmsReceiveDetail> wr_doc_num = smsWmsReceiveDetailService.list(new QueryWrapper<SmsWmsReceiveDetail>()
                .eq("WR_DOC_NUM", wrDocNum));
        List<SmsWmsReceiveDetailVO> smsWmsReceiveDetailVOS=new ArrayList<>();
        for (SmsWmsReceiveDetail smsWmsReceiveDetail : wr_doc_num) {
            SmsWmsReceiveDetailVO smsWmsReceiveDetailVO=new SmsWmsReceiveDetailVO();
            SmsWmsPoDet one = smsWmsPoDetService.getOne(new QueryWrapper<SmsWmsPoDet>()
                    .eq("twd_po_no", smsWmsReceiveDetail.getErpPo())
                    .eq("twd_po_item_no", smsWmsReceiveDetail.getErpPoItemNo())
            );
            if (one != null) {
                BeanUtil.copyProperties(smsWmsReceiveDetail,smsWmsReceiveDetailVO);
                smsWmsReceiveDetailVO.setItemName(one.getItemName());
                smsWmsReceiveDetailVO.setManufacturerCode(one.getManufacturerCode());
                smsWmsReceiveDetailVO.setMpn(one.getMpn());
                smsWmsReceiveDetailVO.setTwdDate(one.getTwdDate());
                smsWmsReceiveDetailVO.setTwdSupCode(one.getTwdSupCode());
                smsWmsReceiveDetailVO.setTblManufacturerBat(smsWmsReceiveDetail.getTblManufacturerBat());
                smsWmsReceiveDetailVO.setTblManufacturerDate(smsWmsReceiveDetail.getTblManufacturerDate());
                smsWmsReceiveDetailVOS.add(smsWmsReceiveDetailVO);
            }
        }
        return Result.succeed(smsWmsReceiveDetailVOS, "查询成功");
    }

    @ApiOperation(value = "根据采购入库单号查询入库详情")
    @PostMapping("/smsWmsReceiveDoc/selAllAndBarcodes")
    public Result selAllAndBarcodes(@RequestBody Map<String,Object> map) {
        String wrDocNum = MapUtil.getStr(map, "wrDocNum");
        List<SmsWmsReceiveDetail> wr_doc_num = smsWmsReceiveDetailService.list(new QueryWrapper<SmsWmsReceiveDetail>()
                .eq("WR_DOC_NUM", wrDocNum));
        List<SmsWmsReceiveDetailVO> smsWmsReceiveDetailVOS=new ArrayList<>();
        for (SmsWmsReceiveDetail smsWmsReceiveDetail : wr_doc_num) {
            SmsWmsReceiveDetailVO smsWmsReceiveDetailVO=new SmsWmsReceiveDetailVO();
            SmsWmsPoDet one = smsWmsPoDetService.getOne(new QueryWrapper<SmsWmsPoDet>()
                    .eq("twd_po_no", smsWmsReceiveDetail.getErpPo())
                    .eq("twd_po_item_no", smsWmsReceiveDetail.getErpPoItemNo())
            );
            if (one != null) {
                BeanUtil.copyProperties(smsWmsReceiveDetail,smsWmsReceiveDetailVO);
                smsWmsReceiveDetailVO.setItemName(one.getItemName());
                smsWmsReceiveDetailVO.setManufacturerCode(one.getManufacturerCode());
                smsWmsReceiveDetailVO.setMpn(one.getMpn());
                smsWmsReceiveDetailVO.setTwdDate(one.getTwdDate());
                smsWmsReceiveDetailVO.setTwdSupCode(one.getTwdSupCode());
                smsWmsReceiveDetailVO.setTblManufacturerBat(smsWmsReceiveDetail.getTblManufacturerBat());
                smsWmsReceiveDetailVO.setTblManufacturerDate(smsWmsReceiveDetail.getTblManufacturerDate());
                smsWmsReceiveDetailVOS.add(smsWmsReceiveDetailVO);
            }
        }
        List<Map<String, Object>> smsWmsBarcodeInfos = smsWmsBarcodeInfoService.getNotPdaBarcodes(wrDocNum);
        Map<String, Object> result=new HashMap<>();
        result.put("smsWmsReceiveDetailVOS",smsWmsReceiveDetailVOS);
        result.put("smsWmsBarcodeInfos",smsWmsBarcodeInfos);
        return Result.succeed(result, "查询成功");
    }
    @ApiOperation(value = "根据换料入库单号查询入库详情")
    @PostMapping("/smsWmsReceiveDoc/selReceiveAll")
    public Result selReceiveAll(@RequestBody Map<String,Object> map) {
        String wrDocNum = MapUtil.getStr(map, "wrDocNum");
        List<SmsWmsReceiveDetail> smsWmsReceiveDetails = smsWmsReceiveDetailService.list(new QueryWrapper<SmsWmsReceiveDetail>()
                .eq("WR_DOC_NUM", wrDocNum));
        return Result.succeed(smsWmsReceiveDetails, "查询成功");
    }
    @ApiOperation(value = "根据其他入库单号查询入库详情")
    @PostMapping("/smsWmsReceiveDoc/otherWarehouseSelAll")
    public Result otherWarehouseSelAll(@RequestBody Map<String,Object> map) {
        String wrDocNum = MapUtil.getStr(map, "wrDocNum");
        SmsWmsReceiveDoc receiveDoc = smsWmsReceiveDocService.getOne(new QueryWrapper<SmsWmsReceiveDoc>()
                .eq("WR_DOC_NUM", wrDocNum));
        Map<String, Object> result = BeanUtil.beanToMap(receiveDoc);
        List<SmsWmsReceiveDetail> receiveDetails = smsWmsReceiveDetailService.list(new QueryWrapper<SmsWmsReceiveDetail>()
                .eq("WR_DOC_NUM", wrDocNum));
        
        List<Map<String, Object>> receiveDetailMaps=new ArrayList<>();
        
        for (SmsWmsReceiveDetail receiveDetail : receiveDetails) {
            Map<String, Object> receiveDetailMap = BeanUtil.beanToMap(receiveDetail);
            String coItemCode = receiveDetail.getCoItemCode();
            UmsItemSap itemSap = umsItemSapService.getOne(new QueryWrapper<UmsItemSap>().eq("CO_ITEM_CODE", coItemCode));
            String projectId = receiveDetail.getProjectId();
            if (!StrUtil.isEmpty(projectId)) {
                projectId=projectId.split(",")[0];
            }
            receiveDetailMap.put("mpn",itemSap.getMpn());
            receiveDetailMap.put("itemMinPack",itemSap.getIoItemMinPack());
            receiveDetailMap.put("productCode","");
            receiveDetailMap.put("productName","");
            receiveDetailMap.put("productDesc","");
            if (!StrUtil.isEmpty(projectId)) {
                WmsProjectBase one = wmsProjectBaseService.getOne(new QueryWrapper<WmsProjectBase>().eq("project_id", projectId));
                if (one != null) {
                    receiveDetailMap.put("productCode",one.getItemCode());
                    receiveDetailMap.put("productName",one.getItemName());
                    receiveDetailMap.put("productDesc",one.getItemDesc());
                }
            }
            receiveDetailMaps.add(receiveDetailMap);
        }
        List<Map<String, Object>> smsWmsBarcodeInfos = smsWmsBarcodeInfoService.getNotPdaBarcodes(wrDocNum);
        result.put("smsWmsBarcodeInfos",smsWmsBarcodeInfos);
        result.put("receiveDetails",receiveDetailMaps);
        return Result.succeed(result, "查询成功");
    }
    
    

    @ApiOperation(value = "删除全部")
    @PostMapping("/smsWmsReceiveDoc/deleteAll")
    public Result deleteAll(@RequestBody Map<String,List<String>> map) {
        return smsWmsReceiveDocService.deleteAll(map);
    }
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/smsWmsReceiveDoc/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= smsWmsReceiveDocService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    @ApiOperation(value = "采购订单管理列表")
    @PostMapping("/smsWmsReceiveDoc/allList")
    public Result<PageResult> allList(@RequestBody Map<String, Object> params) {
        String code = MapUtil.getStr(params, "depaCode");
        List<String> umsDepas = StrUtil.isNotEmpty(code)?umsDepaService.getSon(params):new ArrayList<>();
        params.put("umsDepas",umsDepas);
        Page<Map> list= smsWmsReceiveDocService.allList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    @ApiOperation(value = "采购订单查询列表")
    @PostMapping("/smsWmsReceiveDoc/allSelectList")
    public Result<PageResult> allSelectList(@RequestBody Map<String, Object> params) {
        String code = MapUtil.getStr(params, "depaCode");
        List<String> umsDepas = StrUtil.isNotEmpty(code)?umsDepaService.getSon(params):new ArrayList<>();
        params.put("umsDepas",umsDepas);
        Page<Map> list= smsWmsReceiveDocService.allSelectList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/smsWmsReceiveDoc/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SmsWmsReceiveDoc model = smsWmsReceiveDocService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/smsWmsReceiveDoc/save")
    public Result save(@RequestBody SmsWmsReceiveDoc smsWmsReceiveDoc) {
        smsWmsReceiveDocService.saveOrUpdate(smsWmsReceiveDoc);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/smsWmsReceiveDoc/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        smsWmsReceiveDocService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/smsWmsReceiveDoc/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SmsWmsReceiveDoc> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SmsWmsReceiveDoc.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        smsWmsReceiveDocService.save(u);
                });
                return Result.succeed("成功导入信息"+rowNum+"行数据");
            }
        }
        return Result.failed("导入失败");
    }
    
    /**
     * 导出
     */
    @ApiOperation(value = "导出")
    @PostMapping("/smsWmsReceiveDoc/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<SmsWmsReceiveDoc> smsWmsReceiveDocs =new ArrayList<>();
        List<SmsWmsReceiveDoc> smsWmsReceiveDocList = smsWmsReceiveDocService.list(new QueryWrapper<SmsWmsReceiveDoc>().eq("cu_id", cuId));
        if (smsWmsReceiveDocList.isEmpty()) {smsWmsReceiveDocs.add(smsWmsReceiveDocService.getById(0)); } else {
            for (SmsWmsReceiveDoc smsWmsReceiveDoc : smsWmsReceiveDocList) {
                smsWmsReceiveDocs.add(smsWmsReceiveDoc);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(smsWmsReceiveDocs, "入库单表导出", "入库单表导出", SmsWmsReceiveDoc.class, "smsWmsReceiveDoc.xls", response);

    }
}
