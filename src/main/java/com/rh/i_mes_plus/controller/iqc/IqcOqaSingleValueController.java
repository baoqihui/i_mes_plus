package com.rh.i_mes_plus.controller.iqc;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.iqc.IqcOqaSingleValue;
import com.rh.i_mes_plus.service.iqc.IIqcOqaSingleValueService;
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
 * 样品检测项目值
 *
 * @author hbq
 * @date 2020-10-22 16:28:19
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "样品检测项目值")
@RequestMapping("iqc")
public class IqcOqaSingleValueController {
    @Autowired
    private IIqcOqaSingleValueService iqcOqaSingleValueService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/iqcOqaSingleValue/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= iqcOqaSingleValueService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/iqcOqaSingleValue/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        IqcOqaSingleValue model = iqcOqaSingleValueService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/iqcOqaSingleValue/save")
    public Result save(@RequestBody IqcOqaSingleValue iqcOqaSingleValue) {
        iqcOqaSingleValueService.saveOrUpdate(iqcOqaSingleValue);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/iqcOqaSingleValue/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<IqcOqaSingleValue>> map) {
        List<IqcOqaSingleValue> models = map.get("models");
        iqcOqaSingleValueService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/iqcOqaSingleValue/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        iqcOqaSingleValueService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/iqcOqaSingleValue/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<IqcOqaSingleValue> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, IqcOqaSingleValue.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        iqcOqaSingleValueService.save(u);
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
    @PostMapping("/iqcOqaSingleValue/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<IqcOqaSingleValue> iqcOqaSingleValues =new ArrayList<>();
        List<IqcOqaSingleValue> iqcOqaSingleValueList = iqcOqaSingleValueService.list(new QueryWrapper<IqcOqaSingleValue>().eq("cu_id", cuId));
        if (iqcOqaSingleValueList.isEmpty()) {iqcOqaSingleValues.add(iqcOqaSingleValueService.getById(0)); } else {
            for (IqcOqaSingleValue iqcOqaSingleValue : iqcOqaSingleValueList) {
                iqcOqaSingleValues.add(iqcOqaSingleValue);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(iqcOqaSingleValues, "样品检测项目值导出", "样品检测项目值导出", IqcOqaSingleValue.class, "iqcOqaSingleValue.xls", response);

    }
}
