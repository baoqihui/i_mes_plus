package com.rh.i_mes_plus.controller.gtoa;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.gtoa.ExecuteModeInfo;
import com.rh.i_mes_plus.service.gtoa.IExecuteModeInfoService;
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
 * 执行方式表
 *
 * @author hbq
 * @date 2020-10-22 19:36:56
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "执行方式表")
@RequestMapping("exe")
public class ExecuteModeInfoController {
    @Autowired
    private IExecuteModeInfoService executeModeInfoService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/executeModeInfo/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= executeModeInfoService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/executeModeInfo/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        ExecuteModeInfo model = executeModeInfoService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/executeModeInfo/save")
    public Result save(@RequestBody ExecuteModeInfo executeModeInfo) {
        executeModeInfoService.saveOrUpdate(executeModeInfo);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/executeModeInfo/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<ExecuteModeInfo>> map) {
        List<ExecuteModeInfo> models = map.get("models");
        executeModeInfoService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/executeModeInfo/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        executeModeInfoService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/executeModeInfo/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<ExecuteModeInfo> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, ExecuteModeInfo.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        executeModeInfoService.save(u);
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
    @PostMapping("/executeModeInfo/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<ExecuteModeInfo> executeModeInfos =new ArrayList<>();
        List<ExecuteModeInfo> executeModeInfoList = executeModeInfoService.list(new QueryWrapper<ExecuteModeInfo>().eq("cu_id", cuId));
        if (executeModeInfoList.isEmpty()) {executeModeInfos.add(executeModeInfoService.getById(0)); } else {
            for (ExecuteModeInfo executeModeInfo : executeModeInfoList) {
                executeModeInfos.add(executeModeInfo);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(executeModeInfos, "执行方式表导出", "执行方式表导出", ExecuteModeInfo.class, "executeModeInfo.xls", response);

    }
}
