package com.rh.i_mes_plus.controller.ums;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.ums.UmsWmsMsdControlRule;
import com.rh.i_mes_plus.service.ums.IUmsWmsMsdControlRuleService;
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
 * MSD管控规则
 *
 * @author hqb
 * @date 2020-09-21 16:35:09
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "MSD管控规则")
@RequestMapping("ums")
public class UmsWmsMsdControlRuleController {
    @Autowired
    private IUmsWmsMsdControlRuleService umsWmsMsdControlRuleService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/umsWmsMsdControlRule/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<UmsWmsMsdControlRule> list= umsWmsMsdControlRuleService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/umsWmsMsdControlRule/sel/{id}")
    public Result findUserById(@PathVariable Long mcrId) {
        UmsWmsMsdControlRule model = umsWmsMsdControlRuleService.getById(mcrId);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/umsWmsMsdControlRule/save")
    public Result save(@RequestBody UmsWmsMsdControlRule umsWmsMsdControlRule) {
        umsWmsMsdControlRuleService.saveOrUpdate(umsWmsMsdControlRule);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/umsWmsMsdControlRule/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        umsWmsMsdControlRuleService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/umsWmsMsdControlRule/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<UmsWmsMsdControlRule> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, UmsWmsMsdControlRule.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        umsWmsMsdControlRuleService.save(u);
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
    @PostMapping("/umsWmsMsdControlRule/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<UmsWmsMsdControlRule> umsWmsMsdControlRules =new ArrayList<>();
        List<UmsWmsMsdControlRule> umsWmsMsdControlRuleList = umsWmsMsdControlRuleService.list(new QueryWrapper<UmsWmsMsdControlRule>().eq("cu_id", cuId));
        if (umsWmsMsdControlRuleList.isEmpty()) {umsWmsMsdControlRules.add(umsWmsMsdControlRuleService.getById(0)); } else {
            for (UmsWmsMsdControlRule umsWmsMsdControlRule : umsWmsMsdControlRuleList) {
                umsWmsMsdControlRules.add(umsWmsMsdControlRule);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(umsWmsMsdControlRules, "MSD管控规则导出", "MSD管控规则导出", UmsWmsMsdControlRule.class, "umsWmsMsdControlRule.xls", response);

    }
}
