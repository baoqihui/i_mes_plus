package com.rh.i_mes_plus.controller.sms;
import java.io.IOException;
import java.util.List;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;


import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import com.rh.i_mes_plus.util.EasyPoiExcelUtil;
import com.rh.i_mes_plus.model.sms.SmsLightColor;
import com.rh.i_mes_plus.service.sms.ISmsLightColorService;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;

/**
 * 仓库灯资源占用
 *
 * @author hbq
 * @date 2021-06-24 15:19:55
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "仓库灯资源占用")
@RequestMapping("sms")
public class SmsLightColorController {
    @Autowired
    private ISmsLightColorService smsLightColorService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/smsLightColor/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= smsLightColorService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/smsLightColor/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SmsLightColor model = smsLightColorService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/smsLightColor/save")
    public Result save(@RequestBody SmsLightColor smsLightColor) {
        smsLightColorService.saveOrUpdate(smsLightColor);
        return Result.succeed("保存成功");
    }

    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/smsLightColor/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<SmsLightColor>> map) {
        List<SmsLightColor> models = map.get("models");
        smsLightColorService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/smsLightColor/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        smsLightColorService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/smsLightColor/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SmsLightColor> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SmsLightColor.class);
            rowNum = list.size();
            if (rowNum > 0) {
                list.forEach(u -> {
                        smsLightColorService.save(u);
                });
                return Result.succeed("成功导入信息"+rowNum+"行数据");
            }
        }
        return Result.failed("导入失败");
    }
    
    /**
     * 导出（传入ids数组，选择指定id）
     */
    @ApiOperation(value = "导出（传入ids数组，选择指定id）")
    @PostMapping("/smsLightColor/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<SmsLightColor> smsLightColorList = ids==null||ids.isEmpty()? smsLightColorService.list():(List)smsLightColorService.listByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(smsLightColorList, "仓库灯资源占用导出", "仓库灯资源占用导出", SmsLightColor.class, "smsLightColor.xls", response);
    }
}
