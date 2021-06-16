package com.rh.i_mes_plus.controller.other;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.other.PdaMesLog;
import com.rh.i_mes_plus.service.other.IPdaMesLogService;
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
 * pda与mes操作日志
 *
 * @author hbq
 * @date 2021-01-28 17:56:25
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "pda与mes操作日志")
@RequestMapping("pda")
public class PdaMesLogController {
    @Autowired
    private IPdaMesLogService pdaMesLogService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/pdaMesLog/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= pdaMesLogService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/pdaMesLog/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        PdaMesLog model = pdaMesLogService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/pdaMesLog/save")
    public Result save(@RequestBody PdaMesLog pdaMesLog) {
        pdaMesLogService.saveOrUpdate(pdaMesLog);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/pdaMesLog/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<PdaMesLog>> map) {
        List<PdaMesLog> models = map.get("models");
        pdaMesLogService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/pdaMesLog/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        pdaMesLogService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/pdaMesLog/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<PdaMesLog> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, PdaMesLog.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        pdaMesLogService.save(u);
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
    @PostMapping("/pdaMesLog/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<PdaMesLog> pdaMesLogs =new ArrayList<>();
        List<PdaMesLog> pdaMesLogList = pdaMesLogService.list(new QueryWrapper<PdaMesLog>().eq("cu_id", cuId));
        if (pdaMesLogList.isEmpty()) {pdaMesLogs.add(pdaMesLogService.getById(0)); } else {
            for (PdaMesLog pdaMesLog : pdaMesLogList) {
                pdaMesLogs.add(pdaMesLog);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(pdaMesLogs, "pda与mes操作日志导出", "pda与mes操作日志导出", PdaMesLog.class, "pdaMesLog.xls", response);

    }
}
