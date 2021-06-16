package com.rh.i_mes_plus.controller.sms;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sms.SmsWmsBarcodeInfoLog;
import com.rh.i_mes_plus.service.sms.ISmsWmsBarcodeInfoLogService;
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
 * 条码信息(临时表)
 *
 * @author hqb
 * @date 2020-10-07 14:27:41
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "条码信息(临时表)")
@RequestMapping("sms")
public class SmsWmsBarcodeInfoLogController {
    @Autowired
    private ISmsWmsBarcodeInfoLogService smsWmsBarcodeInfoLogService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/smsWmsBarcodeInfoLog/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<SmsWmsBarcodeInfoLog> list= smsWmsBarcodeInfoLogService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/smsWmsBarcodeInfoLog/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SmsWmsBarcodeInfoLog model = smsWmsBarcodeInfoLogService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/smsWmsBarcodeInfoLog/save")
    public Result save(@RequestBody SmsWmsBarcodeInfoLog smsWmsBarcodeInfoLog) {
        smsWmsBarcodeInfoLogService.saveOrUpdate(smsWmsBarcodeInfoLog);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/smsWmsBarcodeInfoLog/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        smsWmsBarcodeInfoLogService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/smsWmsBarcodeInfoLog/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SmsWmsBarcodeInfoLog> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SmsWmsBarcodeInfoLog.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        smsWmsBarcodeInfoLogService.save(u);
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
    @PostMapping("/smsWmsBarcodeInfoLog/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<SmsWmsBarcodeInfoLog> smsWmsBarcodeInfoLogs =new ArrayList<>();
        List<SmsWmsBarcodeInfoLog> smsWmsBarcodeInfoLogList = smsWmsBarcodeInfoLogService.list(new QueryWrapper<SmsWmsBarcodeInfoLog>().eq("cu_id", cuId));
        if (smsWmsBarcodeInfoLogList.isEmpty()) {smsWmsBarcodeInfoLogs.add(smsWmsBarcodeInfoLogService.getById(0)); } else {
            for (SmsWmsBarcodeInfoLog smsWmsBarcodeInfoLog : smsWmsBarcodeInfoLogList) {
                smsWmsBarcodeInfoLogs.add(smsWmsBarcodeInfoLog);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(smsWmsBarcodeInfoLogs, "条码信息(临时表)导出", "条码信息(临时表)导出", SmsWmsBarcodeInfoLog.class, "smsWmsBarcodeInfoLog.xls", response);

    }
}
