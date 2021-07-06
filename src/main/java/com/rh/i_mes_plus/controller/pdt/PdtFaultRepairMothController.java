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
import com.rh.i_mes_plus.model.pdt.PdtFaultRepairMoth;
import com.rh.i_mes_plus.service.pdt.IPdtFaultRepairMothService;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;

/**
 * 维修方法
 *
 * @author hbq
 * @date 2021-07-05 10:11:08
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "维修方法")
@RequestMapping("pdt")
public class PdtFaultRepairMothController {
    @Autowired
    private IPdtFaultRepairMothService pdtFaultRepairMothService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/pdtFaultRepairMoth/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= pdtFaultRepairMothService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/pdtFaultRepairMoth/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        PdtFaultRepairMoth model = pdtFaultRepairMothService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/pdtFaultRepairMoth/save")
    public Result save(@RequestBody PdtFaultRepairMoth pdtFaultRepairMoth) {
        pdtFaultRepairMothService.saveOrUpdate(pdtFaultRepairMoth);
        return Result.succeed("保存成功");
    }

    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/pdtFaultRepairMoth/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<PdtFaultRepairMoth>> map) {
        List<PdtFaultRepairMoth> models = map.get("models");
        pdtFaultRepairMothService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/pdtFaultRepairMoth/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        pdtFaultRepairMothService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/pdtFaultRepairMoth/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<PdtFaultRepairMoth> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, PdtFaultRepairMoth.class);
            rowNum = list.size();
            if (rowNum > 0) {
                list.forEach(u -> {
                        pdtFaultRepairMothService.save(u);
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
    @PostMapping("/pdtFaultRepairMoth/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<PdtFaultRepairMoth> pdtFaultRepairMothList = ids==null||ids.isEmpty()? pdtFaultRepairMothService.list():(List)pdtFaultRepairMothService.listByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(pdtFaultRepairMothList, "维修方法导出", "维修方法导出", PdtFaultRepairMoth.class, "pdtFaultRepairMoth.xls", response);
    }
}
