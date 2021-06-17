package com.rh.i_mes_plus.controller.iqc;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.iqc.IqcOqaSingle;
import com.rh.i_mes_plus.service.iqc.IIqcOqaSingleService;
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
 * 抽验样本信息
 *
 * @author hbq
 * @date 2020-10-23 11:39:35
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "抽验样本信息")
@RequestMapping("iqc")
public class IqcOqaSingleController {
    @Autowired
    private IIqcOqaSingleService iqcOqaSingleService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/iqcOqaSingle/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= iqcOqaSingleService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/iqcOqaSingle/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        IqcOqaSingle model = iqcOqaSingleService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/iqcOqaSingle/save")
    public Result save(@RequestBody IqcOqaSingle iqcOqaSingle) {
        iqcOqaSingleService.saveOrUpdate(iqcOqaSingle);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/iqcOqaSingle/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<IqcOqaSingle>> map) {
        List<IqcOqaSingle> models = map.get("models");
        iqcOqaSingleService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/iqcOqaSingle/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        iqcOqaSingleService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/iqcOqaSingle/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<IqcOqaSingle> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, IqcOqaSingle.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        iqcOqaSingleService.save(u);
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
    @PostMapping("/iqcOqaSingle/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<IqcOqaSingle> iqcOqaSingles =new ArrayList<>();
        List<IqcOqaSingle> iqcOqaSingleList = iqcOqaSingleService.list(new QueryWrapper<IqcOqaSingle>().eq("cu_id", cuId));
        if (iqcOqaSingleList.isEmpty()) {iqcOqaSingles.add(iqcOqaSingleService.getById(0)); } else {
            for (IqcOqaSingle iqcOqaSingle : iqcOqaSingleList) {
                iqcOqaSingles.add(iqcOqaSingle);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(iqcOqaSingles, "抽验样本信息导出", "抽验样本信息导出", IqcOqaSingle.class, "iqcOqaSingle.xls", response);

    }
}
