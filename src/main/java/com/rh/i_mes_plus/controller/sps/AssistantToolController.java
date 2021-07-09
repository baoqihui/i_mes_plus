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
import com.rh.i_mes_plus.model.sps.AssistantTool;
import com.rh.i_mes_plus.service.sps.IAssistantToolService;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;

/**
 * 辅料信息
 *
 * @author hbq
 * @date 2021-07-08 19:48:01
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "辅料信息")
@RequestMapping("ass")
public class AssistantToolController {
    @Autowired
    private IAssistantToolService assistantToolService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/assistantTool/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= assistantToolService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/assistantTool/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        AssistantTool model = assistantToolService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/assistantTool/save")
    public Result save(@RequestBody AssistantTool assistantTool) {
        assistantToolService.saveOrUpdate(assistantTool);
        return Result.succeed("保存成功");
    }

    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/assistantTool/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<AssistantTool>> map) {
        List<AssistantTool> models = map.get("models");
        assistantToolService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/assistantTool/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        assistantToolService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/assistantTool/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<AssistantTool> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, AssistantTool.class);
            rowNum = list.size();
            if (rowNum > 0) {
                list.forEach(u -> {
                        assistantToolService.save(u);
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
    @PostMapping("/assistantTool/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<AssistantTool> assistantToolList = ids==null||ids.isEmpty()? assistantToolService.list():(List)assistantToolService.listByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(assistantToolList, "辅料信息导出", "辅料信息导出", AssistantTool.class, "assistantTool.xls", response);
    }
}
