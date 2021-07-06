package com.rh.i_mes_plus.controller.pdt;
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
import com.rh.i_mes_plus.model.pdt.PdtFaultErrorType;
import com.rh.i_mes_plus.service.pdt.IPdtFaultErrorTypeService;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;

/**
 * 不良类型
 *
 * @author hbq
 * @date 2021-07-05 10:11:08
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "不良类型")
@RequestMapping("pdt")
public class PdtFaultErrorTypeController {
    @Autowired
    private IPdtFaultErrorTypeService pdtFaultErrorTypeService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/pdtFaultErrorType/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= pdtFaultErrorTypeService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/pdtFaultErrorType/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        PdtFaultErrorType model = pdtFaultErrorTypeService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/pdtFaultErrorType/save")
    public Result save(@RequestBody PdtFaultErrorType pdtFaultErrorType) {
        pdtFaultErrorTypeService.saveOrUpdate(pdtFaultErrorType);
        return Result.succeed("保存成功");
    }

    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/pdtFaultErrorType/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<PdtFaultErrorType>> map) {
        List<PdtFaultErrorType> models = map.get("models");
        pdtFaultErrorTypeService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/pdtFaultErrorType/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        pdtFaultErrorTypeService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/pdtFaultErrorType/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<PdtFaultErrorType> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, PdtFaultErrorType.class);
            rowNum = list.size();
            if (rowNum > 0) {
                list.forEach(u -> {
                        pdtFaultErrorTypeService.save(u);
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
    @PostMapping("/pdtFaultErrorType/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<PdtFaultErrorType> pdtFaultErrorTypeList = ids==null||ids.isEmpty()? pdtFaultErrorTypeService.list():(List)pdtFaultErrorTypeService.listByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(pdtFaultErrorTypeList, "不良类型导出", "不良类型导出", PdtFaultErrorType.class, "pdtFaultErrorType.xls", response);
    }
}
