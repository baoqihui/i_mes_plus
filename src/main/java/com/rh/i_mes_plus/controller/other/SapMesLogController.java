package com.rh.i_mes_plus.controller.other;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.other.SapMesLog;
import com.rh.i_mes_plus.service.other.ISapMesLogService;
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
 * sap-mes接口错误日志
 *
 * @author hbq
 * @date 2020-10-31 10:58:31
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "sap-mes接口错误日志")
@RequestMapping("sap")
public class SapMesLogController {
    @Autowired
    private ISapMesLogService sapMesLogService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/sapMesLog/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= sapMesLogService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/sapMesLog/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SapMesLog model = sapMesLogService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/sapMesLog/save")
    public Result save(@RequestBody SapMesLog sapMesLog) {
        sapMesLogService.saveOrUpdate(sapMesLog);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/sapMesLog/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<SapMesLog>> map) {
        List<SapMesLog> models = map.get("models");
        sapMesLogService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/sapMesLog/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        sapMesLogService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/sapMesLog/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SapMesLog> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SapMesLog.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        sapMesLogService.save(u);
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
    @PostMapping("/sapMesLog/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<SapMesLog> sapMesLogs =new ArrayList<>();
        List<SapMesLog> sapMesLogList = sapMesLogService.list(new QueryWrapper<SapMesLog>().eq("cu_id", cuId));
        if (sapMesLogList.isEmpty()) {sapMesLogs.add(sapMesLogService.getById(0)); } else {
            for (SapMesLog sapMesLog : sapMesLogList) {
                sapMesLogs.add(sapMesLog);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(sapMesLogs, "sap-mes接口错误日志导出", "sap-mes接口错误日志导出", SapMesLog.class, "sapMesLog.xls", response);

    }
}
