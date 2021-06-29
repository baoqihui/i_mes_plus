package com.rh.i_mes_plus.controller.sms;
import java.io.IOException;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.rh.i_mes_plus.dto.SmsBarcodeRuleDTO;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;


import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import com.rh.i_mes_plus.util.EasyPoiExcelUtil;
import com.rh.i_mes_plus.model.sms.SmsBarcodeRule;
import com.rh.i_mes_plus.service.sms.ISmsBarcodeRuleService;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;

/**
 * 条码规则管理
 *
 * @author hbq
 * @date 2021-06-28 18:41:06
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "条码规则管理")
@RequestMapping("sms")
public class SmsBarcodeRuleController {
    @Autowired
    private ISmsBarcodeRuleService smsBarcodeRuleService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/smsBarcodeRule/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= smsBarcodeRuleService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/smsBarcodeRule/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SmsBarcodeRule model = smsBarcodeRuleService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 保存全部
     */
    @ApiOperation(value = "保存全部")
    @PostMapping("/smsBarcodeRule/saveAll")
    public Result saveAll(@RequestBody SmsBarcodeRuleDTO smsBarcodeRuleDTO) {
        return smsBarcodeRuleService.saveAll(smsBarcodeRuleDTO);
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/smsBarcodeRule/save")
    public Result save(@RequestBody SmsBarcodeRule smsBarcodeRule) {
        if (smsBarcodeRule.getIsDefault()==1){
            smsBarcodeRuleService.update(new LambdaUpdateWrapper<SmsBarcodeRule>()
                    .eq(SmsBarcodeRule::getCustCode,smsBarcodeRule.getCustCode())
                    .eq(SmsBarcodeRule::getIsDefault,1)
                    .set(SmsBarcodeRule::getIsDefault,0)
            );
        }
        smsBarcodeRuleService.saveOrUpdate(smsBarcodeRule);
        return Result.succeed("保存成功");
    }

    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/smsBarcodeRule/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<SmsBarcodeRule>> map) {
        List<SmsBarcodeRule> models = map.get("models");
        smsBarcodeRuleService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/smsBarcodeRule/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        smsBarcodeRuleService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/smsBarcodeRule/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SmsBarcodeRule> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SmsBarcodeRule.class);
            rowNum = list.size();
            if (rowNum > 0) {
                list.forEach(u -> {
                        smsBarcodeRuleService.save(u);
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
    @PostMapping("/smsBarcodeRule/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<SmsBarcodeRule> smsBarcodeRuleList = ids==null||ids.isEmpty()? smsBarcodeRuleService.list():(List)smsBarcodeRuleService.listByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(smsBarcodeRuleList, "条码规则管理导出", "条码规则管理导出", SmsBarcodeRule.class, "smsBarcodeRule.xls", response);
    }
}
