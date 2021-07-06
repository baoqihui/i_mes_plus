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
import com.rh.i_mes_plus.model.pdt.PdtFaultErrorCode;
import com.rh.i_mes_plus.service.pdt.IPdtFaultErrorCodeService;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;

/**
 * 不良代码
 *
 * @author hbq
 * @date 2021-07-05 10:11:08
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "不良代码")
@RequestMapping("pdt")
public class PdtFaultErrorCodeController {
    @Autowired
    private IPdtFaultErrorCodeService pdtFaultErrorCodeService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/pdtFaultErrorCode/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= pdtFaultErrorCodeService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/pdtFaultErrorCode/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        PdtFaultErrorCode model = pdtFaultErrorCodeService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/pdtFaultErrorCode/save")
    public Result save(@RequestBody PdtFaultErrorCode pdtFaultErrorCode) {
        pdtFaultErrorCodeService.saveOrUpdate(pdtFaultErrorCode);
        return Result.succeed("保存成功");
    }

    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/pdtFaultErrorCode/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<PdtFaultErrorCode>> map) {
        List<PdtFaultErrorCode> models = map.get("models");
        pdtFaultErrorCodeService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/pdtFaultErrorCode/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        pdtFaultErrorCodeService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/pdtFaultErrorCode/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<PdtFaultErrorCode> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, PdtFaultErrorCode.class);
            rowNum = list.size();
            if (rowNum > 0) {
                list.forEach(u -> {
                        pdtFaultErrorCodeService.save(u);
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
    @PostMapping("/pdtFaultErrorCode/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<PdtFaultErrorCode> pdtFaultErrorCodeList = ids==null||ids.isEmpty()? pdtFaultErrorCodeService.list():(List)pdtFaultErrorCodeService.listByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(pdtFaultErrorCodeList, "不良代码导出", "不良代码导出", PdtFaultErrorCode.class, "pdtFaultErrorCode.xls", response);
    }
}
