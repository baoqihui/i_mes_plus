package com.rh.i_mes_plus.controller.iqc;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.iqc.IqcOqaBath;
import com.rh.i_mes_plus.service.iqc.IIqcOqaBathService;
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
 * 检验单明细
 *
 * @author hbq
 * @date 2020-10-21 14:29:19
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "检验单明细")
@RequestMapping("iqc")
public class IqcOqaBathController {
    @Autowired
    private IIqcOqaBathService iqcOqaBathService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/iqcOqaBath/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= iqcOqaBathService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/iqcOqaBath/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        IqcOqaBath model = iqcOqaBathService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/iqcOqaBath/save")
    public Result save(@RequestBody IqcOqaBath iqcOqaBath) {
        iqcOqaBathService.saveOrUpdate(iqcOqaBath);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/iqcOqaBath/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<IqcOqaBath>> map) {
        List<IqcOqaBath> models = map.get("models");
        iqcOqaBathService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/iqcOqaBath/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        iqcOqaBathService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/iqcOqaBath/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<IqcOqaBath> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, IqcOqaBath.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        iqcOqaBathService.save(u);
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
    @PostMapping("/iqcOqaBath/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<IqcOqaBath> iqcOqaBaths =new ArrayList<>();
        List<IqcOqaBath> iqcOqaBathList = iqcOqaBathService.list(new QueryWrapper<IqcOqaBath>().eq("cu_id", cuId));
        if (iqcOqaBathList.isEmpty()) {iqcOqaBaths.add(iqcOqaBathService.getById(0)); } else {
            for (IqcOqaBath iqcOqaBath : iqcOqaBathList) {
                iqcOqaBaths.add(iqcOqaBath);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(iqcOqaBaths, "检验单明细导出", "检验单明细导出", IqcOqaBath.class, "iqcOqaBath.xls", response);

    }
}
