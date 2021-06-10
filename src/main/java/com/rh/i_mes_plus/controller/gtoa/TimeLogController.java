package com.rh.i_mes_plus.controller.gtoa;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.gtoa.TimeLog;
import com.rh.i_mes_plus.service.gtoa.ITimeLogService;
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
 * 时间日志
 *
 * @author hbq
 * @date 2020-11-03 19:54:46
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "时间日志")
@RequestMapping("tim")
public class TimeLogController {
    @Autowired
    private ITimeLogService timeLogService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/timeLog/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= timeLogService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/timeLog/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        TimeLog model = timeLogService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/timeLog/save")
    public Result save(@RequestBody TimeLog timeLog) {
        timeLogService.saveOrUpdate(timeLog);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/timeLog/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<TimeLog>> map) {
        List<TimeLog> models = map.get("models");
        timeLogService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/timeLog/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        timeLogService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/timeLog/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<TimeLog> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, TimeLog.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        timeLogService.save(u);
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
    @PostMapping("/timeLog/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<TimeLog> timeLogs =new ArrayList<>();
        List<TimeLog> timeLogList = timeLogService.list(new QueryWrapper<TimeLog>().eq("cu_id", cuId));
        if (timeLogList.isEmpty()) {timeLogs.add(timeLogService.getById(0)); } else {
            for (TimeLog timeLog : timeLogList) {
                timeLogs.add(timeLog);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(timeLogs, "时间日志导出", "时间日志导出", TimeLog.class, "timeLog.xls", response);

    }
}
