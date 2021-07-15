package com.rh.i_mes_plus.controller.sps;
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
import com.rh.i_mes_plus.model.sps.TinLog;
import com.rh.i_mes_plus.service.sps.ITinLogService;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;

/**
 * 锡膏操作日志
 *
 * @author hbq
 * @date 2021-07-15 10:18:07
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "锡膏操作日志")
@RequestMapping("tin")
public class TinLogController {
    @Autowired
    private ITinLogService tinLogService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/tinLog/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= tinLogService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/tinLog/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        TinLog model = tinLogService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/tinLog/save")
    public Result save(@RequestBody TinLog tinLog) {
        tinLogService.saveOrUpdate(tinLog);
        return Result.succeed("保存成功");
    }

    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/tinLog/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<TinLog>> map) {
        List<TinLog> models = map.get("models");
        tinLogService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/tinLog/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        tinLogService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/tinLog/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<TinLog> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, TinLog.class);
            rowNum = list.size();
            if (rowNum > 0) {
                list.forEach(u -> {
                        tinLogService.save(u);
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
    @PostMapping("/tinLog/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<TinLog> tinLogList = ids==null||ids.isEmpty()? tinLogService.list():(List)tinLogService.listByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(tinLogList, "锡膏操作日志导出", "锡膏操作日志导出", TinLog.class, "tinLog.xls", response);
    }
}
