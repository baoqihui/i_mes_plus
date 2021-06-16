package com.rh.i_mes_plus.controller.sms;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sms.SmsCoManufacturer;
import com.rh.i_mes_plus.service.sms.ISmsCoManufacturerService;
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
 * BOM料件制造商表
 *
 * @author hbq
 * @date 2020-10-31 15:56:15
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "BOM料件制造商表")
@RequestMapping("sms")
public class SmsCoManufacturerController {
    @Autowired
    private ISmsCoManufacturerService smsCoManufacturerService;

    @ApiOperation(value = "sap同步制造商数据到mes")
    @PostMapping("/smsCoManufacturer/saveAll")
    public Result saveAll(@RequestBody List<Map<String,Object>> maps) {
        return smsCoManufacturerService.saveAll(maps);
    }
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/smsCoManufacturer/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= smsCoManufacturerService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/smsCoManufacturer/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SmsCoManufacturer model = smsCoManufacturerService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/smsCoManufacturer/save")
    public Result save(@RequestBody SmsCoManufacturer smsCoManufacturer) {
        smsCoManufacturerService.saveOrUpdate(smsCoManufacturer);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/smsCoManufacturer/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<SmsCoManufacturer>> map) {
        List<SmsCoManufacturer> models = map.get("models");
        smsCoManufacturerService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/smsCoManufacturer/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        smsCoManufacturerService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/smsCoManufacturer/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SmsCoManufacturer> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SmsCoManufacturer.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        smsCoManufacturerService.save(u);
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
    @PostMapping("/smsCoManufacturer/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<SmsCoManufacturer> smsCoManufacturers =new ArrayList<>();
        List<SmsCoManufacturer> smsCoManufacturerList = smsCoManufacturerService.list(new QueryWrapper<SmsCoManufacturer>().eq("cu_id", cuId));
        if (smsCoManufacturerList.isEmpty()) {smsCoManufacturers.add(smsCoManufacturerService.getById(0)); } else {
            for (SmsCoManufacturer smsCoManufacturer : smsCoManufacturerList) {
                smsCoManufacturers.add(smsCoManufacturer);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(smsCoManufacturers, "BOM料件制造商表导出", "BOM料件制造商表导出", SmsCoManufacturer.class, "smsCoManufacturer.xls", response);

    }
}
