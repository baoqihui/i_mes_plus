package com.rh.i_mes_plus.controller.iqc;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.iqc.IqcCoQcSamples;
import com.rh.i_mes_plus.service.iqc.IIqcCoQcSamplesService;
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
 * 样本代码
 *
 * @author hbq
 * @date 2020-10-23 15:24:30
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "样本代码(抽样水平)")
@RequestMapping("iqc")
public class IqcCoQcSamplesController {
    @Autowired
    private IIqcCoQcSamplesService iqcCoQcSamplesService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/iqcCoQcSamples/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= iqcCoQcSamplesService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    /**
     * 抽样水平列表
     */
    @ApiOperation(value = "抽样水平列表")
    @PostMapping("/iqcCoQcSamples/selList")
    public Result selList(@RequestBody Map<String, Object> params) {
        List<Map<String, Object>> maps= iqcCoQcSamplesService.selList(params);
        return Result.succeed(maps, "查询成功");
    }
    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/iqcCoQcSamples/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        IqcCoQcSamples model = iqcCoQcSamplesService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/iqcCoQcSamples/save")
    public Result save(@RequestBody IqcCoQcSamples iqcCoQcSamples) {
        iqcCoQcSamplesService.saveOrUpdate(iqcCoQcSamples);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/iqcCoQcSamples/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<IqcCoQcSamples>> map) {
        List<IqcCoQcSamples> models = map.get("models");
        iqcCoQcSamplesService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/iqcCoQcSamples/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        iqcCoQcSamplesService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/iqcCoQcSamples/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<IqcCoQcSamples> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, IqcCoQcSamples.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        iqcCoQcSamplesService.save(u);
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
    @PostMapping("/iqcCoQcSamples/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<IqcCoQcSamples> iqcCoQcSampless =new ArrayList<>();
        List<IqcCoQcSamples> iqcCoQcSamplesList = iqcCoQcSamplesService.list(new QueryWrapper<IqcCoQcSamples>().eq("cu_id", cuId));
        if (iqcCoQcSamplesList.isEmpty()) {iqcCoQcSampless.add(iqcCoQcSamplesService.getById(0)); } else {
            for (IqcCoQcSamples iqcCoQcSamples : iqcCoQcSamplesList) {
                iqcCoQcSampless.add(iqcCoQcSamples);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(iqcCoQcSampless, "样本代码导出", "样本代码导出", IqcCoQcSamples.class, "iqcCoQcSamples.xls", response);

    }
}
