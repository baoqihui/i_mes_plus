package com.rh.i_mes_plus.controller.iqc;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.iqc.IqcCoQcSamples;
import com.rh.i_mes_plus.model.iqc.IqcOqaTestGrade;
import com.rh.i_mes_plus.service.iqc.IIqcCoQcSamplesService;
import com.rh.i_mes_plus.service.iqc.IIqcOqaTestGradeService;
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
 * 检验水平
 *
 * @author hqb
 * @date 2020-10-16 13:20:30
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "检验水平")
@RequestMapping("iqc")
public class IqcOqaTestGradeController {
    @Autowired
    private IIqcOqaTestGradeService iqcOqaTestGradeService;
    @Autowired
    private IIqcCoQcSamplesService iqcCoQcSamplesService;
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/iqcOqaTestGrade/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<IqcOqaTestGrade> list= iqcOqaTestGradeService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/iqcOqaTestGrade/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        IqcOqaTestGrade model = iqcOqaTestGradeService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/iqcOqaTestGrade/save")
    public Result save(@RequestBody IqcOqaTestGrade iqcOqaTestGrade) {
        IqcOqaTestGrade one = iqcOqaTestGradeService.getOne(new QueryWrapper<IqcOqaTestGrade>()
                .eq("ol_id", iqcOqaTestGrade.getOlId())
                .eq("otg_level", iqcOqaTestGrade.getOtgLevel())
        );
        if (one!=null){
            Long id = one.getId();
            List<IqcCoQcSamples> iqcCoQcSamples = iqcCoQcSamplesService.list(new QueryWrapper<IqcCoQcSamples>().eq("otg_id", id));
            if (iqcCoQcSamples==null||iqcCoQcSamples.size()<=0){
               return Result.succeed(one,"保存成功");
            }
            return Result.failed(one,"已存在");
        }
        iqcOqaTestGradeService.save(iqcOqaTestGrade);
        return Result.succeed(iqcOqaTestGrade,"保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/iqcOqaTestGrade/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        iqcOqaTestGradeService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/iqcOqaTestGrade/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<IqcOqaTestGrade> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, IqcOqaTestGrade.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        iqcOqaTestGradeService.save(u);
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
    @PostMapping("/iqcOqaTestGrade/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<IqcOqaTestGrade> iqcOqaTestGrades =new ArrayList<>();
        List<IqcOqaTestGrade> iqcOqaTestGradeList = iqcOqaTestGradeService.list(new QueryWrapper<IqcOqaTestGrade>().eq("cu_id", cuId));
        if (iqcOqaTestGradeList.isEmpty()) {iqcOqaTestGrades.add(iqcOqaTestGradeService.getById(0)); } else {
            for (IqcOqaTestGrade iqcOqaTestGrade : iqcOqaTestGradeList) {
                iqcOqaTestGrades.add(iqcOqaTestGrade);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(iqcOqaTestGrades, "检验水平导出", "检验水平导出", IqcOqaTestGrade.class, "iqcOqaTestGrade.xls", response);

    }
}
