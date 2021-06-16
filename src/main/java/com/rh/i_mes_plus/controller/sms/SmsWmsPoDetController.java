package com.rh.i_mes_plus.controller.sms;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.SmsWmsBarcodeInfoDTO;
import com.rh.i_mes_plus.dto.UmsPoDTO;
import com.rh.i_mes_plus.model.sms.SmsWmsPoDet;
import com.rh.i_mes_plus.service.sms.ISmsWmsPoDetService;
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
 * 采购订单明细
 *
 * @author hqb
 * @date 2020-09-29 15:58:30
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "采购单明细")
@RequestMapping("sms")
public class SmsWmsPoDetController {
    @Autowired
    private ISmsWmsPoDetService smsWmsPoDetService;


    @ApiOperation(value = "sap同步po信息到mes")
    @PostMapping("/smsWmsPoDet/saveAll")
    public Result saveAll(@RequestBody List<UmsPoDTO> umsPoDTO) {
        return smsWmsPoDetService.saveAll(umsPoDTO);
    }

    @ApiOperation(value = "条码生成")
    @PostMapping("/smsWmsPoDet/createBarCode")
    public Result createBarCode(@RequestBody List<SmsWmsBarcodeInfoDTO> smsWmsBarcodeInfoDTOS) {
        return smsWmsPoDetService.createBarCode(smsWmsBarcodeInfoDTOS);
    }
    @ApiOperation(value = "单个条码生成")
    @PostMapping("/smsWmsPoDet/createSingleBarCode")
    public Result createSingleBarCode(@RequestBody Map<String, Object> params) {
        return smsWmsPoDetService.createSingleBarCode(params);
    }
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/smsWmsPoDet/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= smsWmsPoDetService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/smsWmsPoDet/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SmsWmsPoDet model = smsWmsPoDetService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/smsWmsPoDet/save")
    public Result save(@RequestBody SmsWmsPoDet smsWmsPoDet) {
        smsWmsPoDetService.saveOrUpdate(smsWmsPoDet);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/smsWmsPoDet/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        smsWmsPoDetService.removeByIds(ids);
        return Result.succeed("删除成功");
    }

    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/smsWmsPoDet/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SmsWmsPoDet> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SmsWmsPoDet.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                    smsWmsPoDetService.save(u);
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
    @PostMapping("/smsWmsPoDet/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<SmsWmsPoDet> smsWmsPoDets =new ArrayList<>();
        List<SmsWmsPoDet> smsWmsPoDetList = smsWmsPoDetService.list(new QueryWrapper<SmsWmsPoDet>().eq("cu_id", cuId));
        if (smsWmsPoDetList.isEmpty()) {smsWmsPoDets.add(smsWmsPoDetService.getById(0)); } else {
            for (SmsWmsPoDet smsWmsPoDet : smsWmsPoDetList) {
                smsWmsPoDets.add(smsWmsPoDet);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(smsWmsPoDets, "采购单明细导出", "采购单明细导出", SmsWmsPoDet.class, "smsWmsPoDet.xls", response);

    }
}
