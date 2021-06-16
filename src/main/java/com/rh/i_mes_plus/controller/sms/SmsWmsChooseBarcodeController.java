package com.rh.i_mes_plus.controller.sms;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sms.SmsWmsChooseBarcode;
import com.rh.i_mes_plus.service.sms.ISmsWmsChooseBarcodeService;
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
 * 
 *
 * @author hbq
 * @date 2021-02-25 16:12:27
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "挑料表")
@RequestMapping("sms")
public class SmsWmsChooseBarcodeController {
    @Autowired
    private ISmsWmsChooseBarcodeService smsWmsChooseBarcodeService;
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/smsWmsChooseBarcode/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= smsWmsChooseBarcodeService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/smsWmsChooseBarcode/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SmsWmsChooseBarcode model = smsWmsChooseBarcodeService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 挑料
     */
    @ApiOperation(value = "挑料")
    @PostMapping("/smsWmsChooseBarcode/choose")
    public Result choose(@RequestBody Map<String, Object> params) {
        return smsWmsChooseBarcodeService.choose(params);
    }

    /**
     * 取消
     */
    @ApiOperation(value = "取消")
    @PostMapping("/smsWmsChooseBarcode/close")
    public Result close(@RequestBody Map<String, Object> params) {
        return smsWmsChooseBarcodeService.close(params);
    }
    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/smsWmsChooseBarcode/save")
    public Result save(@RequestBody SmsWmsChooseBarcode smsWmsChooseBarcode) {
        smsWmsChooseBarcodeService.saveOrUpdate(smsWmsChooseBarcode);
        return Result.succeed("保存成功");
    }
    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/smsWmsChooseBarcode/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<SmsWmsChooseBarcode>> map) {
        List<SmsWmsChooseBarcode> models = map.get("models");
        smsWmsChooseBarcodeService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/smsWmsChooseBarcode/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        smsWmsChooseBarcodeService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/smsWmsChooseBarcode/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SmsWmsChooseBarcode> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SmsWmsChooseBarcode.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        smsWmsChooseBarcodeService.save(u);
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
    @PostMapping("/smsWmsChooseBarcode/leadOut")
    public void leadOut(HttpServletResponse response) throws IOException {
        List<SmsWmsChooseBarcode> smsWmsChooseBarcodes =new ArrayList<>();
        List<SmsWmsChooseBarcode> smsWmsChooseBarcodeList = smsWmsChooseBarcodeService.list(new QueryWrapper<SmsWmsChooseBarcode>());
        if (smsWmsChooseBarcodeList.isEmpty()) {smsWmsChooseBarcodes.add(smsWmsChooseBarcodeService.getById(0)); } else {
            for (SmsWmsChooseBarcode smsWmsChooseBarcode : smsWmsChooseBarcodeList) {
                smsWmsChooseBarcodes.add(smsWmsChooseBarcode);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(smsWmsChooseBarcodes, "导出", "导出", SmsWmsChooseBarcode.class, "smsWmsChooseBarcode.xls", response);

    }
}
