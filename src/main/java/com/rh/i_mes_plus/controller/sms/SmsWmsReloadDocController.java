package com.rh.i_mes_plus.controller.sms;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.SmsWmsReloadDocDTO;
import com.rh.i_mes_plus.model.sms.SmsWmsReloadDoc;
import com.rh.i_mes_plus.model.sms.SmsWmsReloadDocDetail;
import com.rh.i_mes_plus.model.ums.UmsWmsArea;
import com.rh.i_mes_plus.service.sms.ISmsWmsReloadDocDetailService;
import com.rh.i_mes_plus.service.sms.ISmsWmsReloadDocDetailSubService;
import com.rh.i_mes_plus.service.sms.ISmsWmsReloadDocService;
import com.rh.i_mes_plus.service.ums.IUmsDepaService;
import com.rh.i_mes_plus.service.ums.IUmsWmsAreaService;
import com.rh.i_mes_plus.util.EasyPoiExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 调拨单
 *
 * @author hbq
 * @date 2020-12-16 14:55:14
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "换料单")
@RequestMapping("sms")
public class SmsWmsReloadDocController {
    @Autowired
    private ISmsWmsReloadDocService smsWmsReloadDocService;
    @Autowired
    private IUmsDepaService umsDepaService;
    @Autowired
    private ISmsWmsReloadDocDetailService smsWmsReloadDocDetailService;
    @Autowired
    private IUmsWmsAreaService umsWmsAreaService;
    @Autowired
    private ISmsWmsReloadDocDetailSubService smsWmsReloadDocDetailSubService;
    
    @ApiOperation(value = "PDA扫码换料")
    @PostMapping("/smsWmsReloadDoc/pdaReload")
    public Result pdaReceive(@RequestBody Map<String,Object> map) {
        return smsWmsReloadDocService.pdaReload(map);
    }
    /**
     * 根据条码信息查询同po，同物料
     */
    @ApiOperation(value = "根据条码信息查询同po，同物料")
    @PostMapping("/smsWmsReloadDoc/getStockInfoBySn")
    public Result<PageResult> getStockInfoBySn(@RequestBody Map<String, Object> params) {
        Page<Map> list= smsWmsReloadDocService.getStockInfoBySn(params);
        String tblBarcode = MapUtil.getStr(params, "tblBarcode");
        if (StrUtil.isEmpty(tblBarcode)){
            return Result.failed("请输入SN");
        }
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    /**
     * 保存或更新(更新携带id)
     */
    @ApiOperation(value = "保存或更新(更新携带id)")
    @PostMapping("/smsWmsReloadDoc/saveAll")
    public Result saveAll(@RequestBody SmsWmsReloadDocDTO smsWmsReloadDocDTO) {
        return smsWmsReloadDocService.saveAll(smsWmsReloadDocDTO);
    }
    /**
     * 根据机构代码查询要生成的换料单号
     */
    @ApiOperation(value = "根据机构代码查询要生成的换料单号")
    @PostMapping("/smsWmsReloadDoc/getDocNo")
    public Result getDocNo(@RequestBody Map<String,Object> map) {
        return smsWmsReloadDocService.getDocNo(map);
    }
    
    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/smsWmsReloadDoc/del")
    public Result removeAll(@RequestBody Map<String,List<Long>> map) {
        return smsWmsReloadDocService.removeAll(map);
    }
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/smsWmsReloadDoc/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        String code = MapUtil.getStr(params, "depaCode");
        List<String> umsDepas = StrUtil.isNotEmpty(code)?umsDepaService.getSon(params):new ArrayList<>();
        params.put("umsDepas",umsDepas);
        Page<Map> list= smsWmsReloadDocService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    /**
     * 根据单号查详情
     */
    @ApiOperation(value = "根据单号查详情")
    @PostMapping("/smsWmsReloadDoc/selAllByReloadNo")
    public Result selAllByDocNo(@RequestBody Map<String, Object> params) {
        String reloadNo = MapUtil.getStr(params, "reloadNo");
        SmsWmsReloadDoc smsWmsReloadDoc = smsWmsReloadDocService.getOne(new QueryWrapper<SmsWmsReloadDoc>()
                .eq("reload_no", reloadNo));
        Map<String, Object> smsWmsReloadDocMap = BeanUtil.beanToMap(smsWmsReloadDoc);
        String whCode = smsWmsReloadDoc.getWhCode();
        String whName ="";
        if (!StrUtil.isEmpty(whCode)){
            whName=umsWmsAreaService.getOne(new QueryWrapper<UmsWmsArea>().eq("AR_SN", whCode)).getArName();
        }
        smsWmsReloadDocMap.put("whName",whName);
        List<SmsWmsReloadDocDetail> smsWmsReloadDocDetails = smsWmsReloadDocDetailService.list(new QueryWrapper<SmsWmsReloadDocDetail>().eq("reload_no", reloadNo));
        smsWmsReloadDocMap.put("smsWmsReloadDocDetails",smsWmsReloadDocDetails);
        return Result.succeed(smsWmsReloadDocMap, "查询成功");
    }
    /**
     * 根据单号查发货单物料详情
     */
    @ApiOperation(value = "根据单号查发货单物料详情")
    @PostMapping("/smsWmsReloadDoc/selReceiveDetailByReloadNo")
    public Result selReceiveDetailByReloadNo(@RequestBody Map<String, Object> params) {
        String reloadNo = MapUtil.getStr(params, "reloadNo");
        SmsWmsReloadDoc smsWmsReloadDoc = smsWmsReloadDocService.getOne(new QueryWrapper<SmsWmsReloadDoc>()
                .eq("reload_no", reloadNo));
        Map<String, Object> smsWmsReloadDocMap = BeanUtil.beanToMap(smsWmsReloadDoc);
        List<Map<String,Object>> reloadDocDetailMaps = smsWmsReloadDocDetailService.listAll(reloadNo);
        
        smsWmsReloadDocMap.put("smsWmsReloadDocDetails",reloadDocDetailMaps);
        return Result.succeed(smsWmsReloadDocMap, "查询成功");
    }
    /**
     * 根据单号查List详情
     */
    @ApiOperation(value = "根据单号查List详情")
    @PostMapping("/smsWmsReloadDoc/selListByReloadDid")
    public Result selListByOsdId(@RequestBody Map<String, Object> params) {
        String reloadDid = MapUtil.getStr(params, "reloadDid");
        List<Map<String, Object>> smsWmsReloadDocDetailSubs = smsWmsReloadDocDetailSubService.listByDid(reloadDid);
        return Result.succeed(smsWmsReloadDocDetailSubs, "查询成功");
    }
    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/smsWmsReloadDoc/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SmsWmsReloadDoc model = smsWmsReloadDocService.getById(id);
        return Result.succeed(model, "查询成功");
    }
    
    /**
     * 更新换料单状态
     */
    @ApiOperation(value = "更新换料单状态")
    @PostMapping("/smsWmsReloadDoc/updateSmsWmsReloadDoc")
    public Result save(@RequestBody SmsWmsReloadDoc smsWmsReloadDoc) {
        smsWmsReloadDocService.update(new UpdateWrapper<SmsWmsReloadDoc>()
                .eq("reload_no",smsWmsReloadDoc.getReloadNo())
                .set("reload_status",smsWmsReloadDoc.getReloadStatus())
        );
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/smsWmsReloadDoc/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<SmsWmsReloadDoc>> map) {
        List<SmsWmsReloadDoc> models = map.get("models");
        smsWmsReloadDocService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/smsWmsReloadDoc/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SmsWmsReloadDoc> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SmsWmsReloadDoc.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        smsWmsReloadDocService.save(u);
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
    @PostMapping("/smsWmsReloadDoc/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<SmsWmsReloadDoc> smsWmsReloadDocs =new ArrayList<>();
        List<SmsWmsReloadDoc> smsWmsReloadDocList = smsWmsReloadDocService.list(new QueryWrapper<SmsWmsReloadDoc>().eq("cu_id", cuId));
        if (smsWmsReloadDocList.isEmpty()) {smsWmsReloadDocs.add(smsWmsReloadDocService.getById(0)); } else {
            for (SmsWmsReloadDoc smsWmsReloadDoc : smsWmsReloadDocList) {
                smsWmsReloadDocs.add(smsWmsReloadDoc);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(smsWmsReloadDocs, "调拨单导出", "调拨单导出", SmsWmsReloadDoc.class, "smsWmsReloadDoc.xls", response);

    }
}
