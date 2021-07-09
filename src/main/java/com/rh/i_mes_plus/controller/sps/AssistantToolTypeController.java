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
import com.rh.i_mes_plus.model.sps.AssistantToolType;
import com.rh.i_mes_plus.service.sps.IAssistantToolTypeService;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;

/**
 * 辅料类型
 *
 * @author hbq
 * @date 2021-07-08 19:48:01
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "辅料类型")
@RequestMapping("ass")
public class AssistantToolTypeController {
    @Autowired
    private IAssistantToolTypeService assistantToolTypeService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/assistantToolType/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= assistantToolTypeService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/assistantToolType/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        AssistantToolType model = assistantToolTypeService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/assistantToolType/save")
    public Result save(@RequestBody AssistantToolType assistantToolType) {
        assistantToolTypeService.saveOrUpdate(assistantToolType);
        return Result.succeed("保存成功");
    }

    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/assistantToolType/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<AssistantToolType>> map) {
        List<AssistantToolType> models = map.get("models");
        assistantToolTypeService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/assistantToolType/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        assistantToolTypeService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/assistantToolType/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<AssistantToolType> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, AssistantToolType.class);
            rowNum = list.size();
            if (rowNum > 0) {
                list.forEach(u -> {
                        assistantToolTypeService.save(u);
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
    @PostMapping("/assistantToolType/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<AssistantToolType> assistantToolTypeList = ids==null||ids.isEmpty()? assistantToolTypeService.list():(List)assistantToolTypeService.listByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(assistantToolTypeList, "辅料类型导出", "辅料类型导出", AssistantToolType.class, "assistantToolType.xls", response);
    }
}
