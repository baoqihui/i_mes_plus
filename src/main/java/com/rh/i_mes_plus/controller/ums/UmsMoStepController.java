package com.rh.i_mes_plus.controller.ums;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.ums.UmsMoStep;
import com.rh.i_mes_plus.service.ums.IUmsMoStepService;
import com.rh.i_mes_plus.util.EasyPoiExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 工单阶别(0-SMT,1-WAVE,2-Hand Soldering,3-Assembly,4-Others)
 *
 * @author hbq
 * @date 2021-05-31 10:07:36
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "工单阶别(0-SMT,1-WAVE,2-Hand Soldering,3-Assembly,4-Others)")
@RequestMapping("ums")
public class UmsMoStepController {
    @Autowired
    private IUmsMoStepService umsMoStepService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/umsMoStep/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= umsMoStepService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/umsMoStep/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        UmsMoStep model = umsMoStepService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/umsMoStep/save")
    public Result save(@RequestBody UmsMoStep umsMoStep) {
        umsMoStepService.saveOrUpdate(umsMoStep);
        return Result.succeed("保存成功");
    }

    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/umsMoStep/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<UmsMoStep>> map) {
        List<UmsMoStep> models = map.get("models");
        umsMoStepService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/umsMoStep/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        umsMoStepService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/umsMoStep/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<UmsMoStep> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, UmsMoStep.class);
            rowNum = list.size();
            if (rowNum > 0) {
                list.forEach(u -> {
                        umsMoStepService.save(u);
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
    @PostMapping("/umsMoStep/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<UmsMoStep> umsMoStepList = ids==null||ids.isEmpty()? umsMoStepService.list():(List)umsMoStepService.listByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(umsMoStepList, "工单阶别(0-SMT,1-WAVE,2-Hand Soldering,3-Assembly,4-Others)导出", "工单阶别(0-SMT,1-WAVE,2-Hand Soldering,3-Assembly,4-Others)导出", UmsMoStep.class, "umsMoStep.xls", response);
    }
}
