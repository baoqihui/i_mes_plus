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
import com.rh.i_mes_plus.model.sms.SmsBarcodeRuleItem;
import com.rh.i_mes_plus.service.sms.ISmsBarcodeRuleItemService;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;

/**
 * 物料条码规则
 *
 * @author hbq
 * @date 2021-06-28 18:41:07
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "物料条码规则")
@RequestMapping("sms")
public class SmsBarcodeRuleItemController {
    @Autowired
    private ISmsBarcodeRuleItemService smsBarcodeRuleItemService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/smsBarcodeRuleItem/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= smsBarcodeRuleItemService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/smsBarcodeRuleItem/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SmsBarcodeRuleItem model = smsBarcodeRuleItemService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/smsBarcodeRuleItem/save")
    public Result save(@RequestBody SmsBarcodeRuleItem smsBarcodeRuleItem) {
        smsBarcodeRuleItemService.saveOrUpdate(smsBarcodeRuleItem);
        return Result.succeed("保存成功");
    }

    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/smsBarcodeRuleItem/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<SmsBarcodeRuleItem>> map) {
        List<SmsBarcodeRuleItem> models = map.get("models");
        smsBarcodeRuleItemService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/smsBarcodeRuleItem/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        smsBarcodeRuleItemService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/smsBarcodeRuleItem/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SmsBarcodeRuleItem> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SmsBarcodeRuleItem.class);
            rowNum = list.size();
            if (rowNum > 0) {
                list.forEach(u -> {
                        smsBarcodeRuleItemService.save(u);
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
    @PostMapping("/smsBarcodeRuleItem/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<SmsBarcodeRuleItem> smsBarcodeRuleItemList = ids==null||ids.isEmpty()? smsBarcodeRuleItemService.list():(List)smsBarcodeRuleItemService.listByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(smsBarcodeRuleItemList, "物料条码规则导出", "物料条码规则导出", SmsBarcodeRuleItem.class, "smsBarcodeRuleItem.xls", response);
    }
}
