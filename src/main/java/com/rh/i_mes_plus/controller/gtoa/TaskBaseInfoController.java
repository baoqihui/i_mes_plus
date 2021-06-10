package com.rh.i_mes_plus.controller.gtoa;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.gtoa.TaskBaseInfo;
import com.rh.i_mes_plus.service.gtoa.ITaskBaseInfoService;
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
 * 任务信息
 *
 * @author hbq
 * @date 2020-10-29 18:10:24
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "任务信息")
@RequestMapping("tas")
public class TaskBaseInfoController {
    @Autowired
    private ITaskBaseInfoService taskBaseInfoService;

    /**
     * 通过部门查任务列表
     */
    @ApiOperation(value = "通过部门查任务列表")
    @PostMapping("/taskBaseInfo/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= taskBaseInfoService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/taskBaseInfo/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        TaskBaseInfo model = taskBaseInfoService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/taskBaseInfo/save")
    public Result save(@RequestBody TaskBaseInfo taskBaseInfo) {
        taskBaseInfoService.saveOrUpdate(taskBaseInfo);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/taskBaseInfo/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<TaskBaseInfo>> map) {
        List<TaskBaseInfo> models = map.get("models");
        taskBaseInfoService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/taskBaseInfo/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        taskBaseInfoService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/taskBaseInfo/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<TaskBaseInfo> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, TaskBaseInfo.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        taskBaseInfoService.save(u);
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
    @PostMapping("/taskBaseInfo/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<TaskBaseInfo> taskBaseInfos =new ArrayList<>();
        List<TaskBaseInfo> taskBaseInfoList = taskBaseInfoService.list(new QueryWrapper<TaskBaseInfo>().eq("cu_id", cuId));
        if (taskBaseInfoList.isEmpty()) {taskBaseInfos.add(taskBaseInfoService.getById(0)); } else {
            for (TaskBaseInfo taskBaseInfo : taskBaseInfoList) {
                taskBaseInfos.add(taskBaseInfo);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(taskBaseInfos, "任务信息导出", "任务信息导出", TaskBaseInfo.class, "taskBaseInfo.xls", response);

    }
}
