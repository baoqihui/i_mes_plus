package com.rh.i_mes_plus.controller.gtoa;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.gtoa.EcnDetailAuditLog;
import com.rh.i_mes_plus.service.gtoa.IEcnDetailAuditLogService;
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
 * ECN详情表
 *
 * @author hbq
 * @date 2020-10-26 18:58:29
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "ECN详情审核日志表")
@RequestMapping("ecn")
public class EcnDetailAuditLogController {
    @Autowired
    private IEcnDetailAuditLogService ecnDetailAuditLogService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/ecnDetailAuditLog/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= ecnDetailAuditLogService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/ecnDetailAuditLog/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        EcnDetailAuditLog model = ecnDetailAuditLogService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/ecnDetailAuditLog/save")
    public Result save(@RequestBody EcnDetailAuditLog ecnDetailAuditLog) {
        ecnDetailAuditLogService.saveOrUpdate(ecnDetailAuditLog);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/ecnDetailAuditLog/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<EcnDetailAuditLog>> map) {
        List<EcnDetailAuditLog> models = map.get("models");
        ecnDetailAuditLogService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/ecnDetailAuditLog/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        ecnDetailAuditLogService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/ecnDetailAuditLog/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<EcnDetailAuditLog> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, EcnDetailAuditLog.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        ecnDetailAuditLogService.save(u);
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
    @PostMapping("/ecnDetailAuditLog/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<EcnDetailAuditLog> ecnDetailAuditLogs =new ArrayList<>();
        List<EcnDetailAuditLog> ecnDetailAuditLogList = ecnDetailAuditLogService.list(new QueryWrapper<EcnDetailAuditLog>().eq("cu_id", cuId));
        if (ecnDetailAuditLogList.isEmpty()) {ecnDetailAuditLogs.add(ecnDetailAuditLogService.getById(0)); } else {
            for (EcnDetailAuditLog ecnDetailAuditLog : ecnDetailAuditLogList) {
                ecnDetailAuditLogs.add(ecnDetailAuditLog);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(ecnDetailAuditLogs, "ECN详情审核日志表导出", "ECN详情审核日志表导出", EcnDetailAuditLog.class, "ecnDetailAuditLog.xls", response);

    }
}
