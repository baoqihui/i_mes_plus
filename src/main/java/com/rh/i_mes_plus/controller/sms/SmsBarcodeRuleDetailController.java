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
import com.rh.i_mes_plus.model.sms.SmsBarcodeRuleDetail;
import com.rh.i_mes_plus.service.sms.ISmsBarcodeRuleDetailService;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;

/**
 * 条码规则详情
 *
 * @author hbq
 * @date 2021-06-28 18:41:07
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "条码规则详情")
@RequestMapping("sms")
public class SmsBarcodeRuleDetailController {
    @Autowired
    private ISmsBarcodeRuleDetailService smsBarcodeRuleDetailService;
    @ApiOperation(value = "上移 ")
    @PostMapping("/smsBarcodeRuleDetail/upper")
    public Result increase(@RequestBody Map<String,Object> map) {
        return smsBarcodeRuleDetailService.presentToBefore(map);
    }
    @ApiOperation(value = "下移 ")
    @PostMapping("/smsBarcodeRuleDetail/lower")
    public Result lower(@RequestBody Map<String,Object> map) {
        return smsBarcodeRuleDetailService.presentToAfter(map);
    }
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/smsBarcodeRuleDetail/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= smsBarcodeRuleDetailService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/smsBarcodeRuleDetail/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SmsBarcodeRuleDetail model = smsBarcodeRuleDetailService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/smsBarcodeRuleDetail/save")
    public Result save(@RequestBody SmsBarcodeRuleDetail smsBarcodeRuleDetail) {
        smsBarcodeRuleDetailService.saveOrUpdate(smsBarcodeRuleDetail);
        return Result.succeed("保存成功");
    }

    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/smsBarcodeRuleDetail/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<SmsBarcodeRuleDetail>> map) {
        List<SmsBarcodeRuleDetail> models = map.get("models");
        smsBarcodeRuleDetailService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/smsBarcodeRuleDetail/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        smsBarcodeRuleDetailService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/smsBarcodeRuleDetail/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SmsBarcodeRuleDetail> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SmsBarcodeRuleDetail.class);
            rowNum = list.size();
            if (rowNum > 0) {
                list.forEach(u -> {
                        smsBarcodeRuleDetailService.save(u);
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
    @PostMapping("/smsBarcodeRuleDetail/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<SmsBarcodeRuleDetail> smsBarcodeRuleDetailList = ids==null||ids.isEmpty()? smsBarcodeRuleDetailService.list():(List)smsBarcodeRuleDetailService.listByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(smsBarcodeRuleDetailList, "条码规则详情导出", "条码规则详情导出", SmsBarcodeRuleDetail.class, "smsBarcodeRuleDetail.xls", response);
    }
}
