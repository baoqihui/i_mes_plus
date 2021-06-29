package com.rh.i_mes_plus.controller.sms;
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
import com.rh.i_mes_plus.model.sms.SmsBarcodeType;
import com.rh.i_mes_plus.service.sms.ISmsBarcodeTypeService;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;

/**
 * 条码类型
 *
 * @author hbq
 * @date 2021-06-28 18:41:07
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "条码类型")
@RequestMapping("sms")
public class SmsBarcodeTypeController {
    @Autowired
    private ISmsBarcodeTypeService smsBarcodeTypeService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/smsBarcodeType/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= smsBarcodeTypeService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/smsBarcodeType/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SmsBarcodeType model = smsBarcodeTypeService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/smsBarcodeType/save")
    public Result save(@RequestBody SmsBarcodeType smsBarcodeType) {
        smsBarcodeTypeService.saveOrUpdate(smsBarcodeType);
        return Result.succeed("保存成功");
    }

    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/smsBarcodeType/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<SmsBarcodeType>> map) {
        List<SmsBarcodeType> models = map.get("models");
        smsBarcodeTypeService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/smsBarcodeType/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        smsBarcodeTypeService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/smsBarcodeType/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SmsBarcodeType> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SmsBarcodeType.class);
            rowNum = list.size();
            if (rowNum > 0) {
                list.forEach(u -> {
                        smsBarcodeTypeService.save(u);
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
    @PostMapping("/smsBarcodeType/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<SmsBarcodeType> smsBarcodeTypeList = ids==null||ids.isEmpty()? smsBarcodeTypeService.list():(List)smsBarcodeTypeService.listByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(smsBarcodeTypeList, "条码类型导出", "条码类型导出", SmsBarcodeType.class, "smsBarcodeType.xls", response);
    }
}
