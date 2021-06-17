package com.rh.i_mes_plus.controller.iqc;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.iqc.IqcOqaTestStep;
import com.rh.i_mes_plus.service.iqc.IIqcOqaTestStepService;
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
 * 检验阶
 *
 * @author hqb
 * @date 2020-10-16 11:39:51
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "检验阶")
@RequestMapping("iqc")
public class IqcOqaTestStepController {
    @Autowired
    private IIqcOqaTestStepService iqcOqaTestStepService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/iqcOqaTestStep/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<IqcOqaTestStep> list= iqcOqaTestStepService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/iqcOqaTestStep/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        IqcOqaTestStep model = iqcOqaTestStepService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/iqcOqaTestStep/save")
    public Result save(@RequestBody IqcOqaTestStep iqcOqaTestStep) {
        iqcOqaTestStepService.saveOrUpdate(iqcOqaTestStep);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/iqcOqaTestStep/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        iqcOqaTestStepService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/iqcOqaTestStep/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<IqcOqaTestStep> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, IqcOqaTestStep.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        iqcOqaTestStepService.save(u);
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
    @PostMapping("/iqcOqaTestStep/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<IqcOqaTestStep> iqcOqaTestSteps =new ArrayList<>();
        List<IqcOqaTestStep> iqcOqaTestStepList = iqcOqaTestStepService.list(new QueryWrapper<IqcOqaTestStep>().eq("cu_id", cuId));
        if (iqcOqaTestStepList.isEmpty()) {iqcOqaTestSteps.add(iqcOqaTestStepService.getById(0)); } else {
            for (IqcOqaTestStep iqcOqaTestStep : iqcOqaTestStepList) {
                iqcOqaTestSteps.add(iqcOqaTestStep);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(iqcOqaTestSteps, "检验阶导出", "检验阶导出", IqcOqaTestStep.class, "iqcOqaTestStep.xls", response);

    }
}
