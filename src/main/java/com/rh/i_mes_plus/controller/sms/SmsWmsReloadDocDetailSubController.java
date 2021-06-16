package com.rh.i_mes_plus.controller.sms;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sms.SmsWmsReloadDocDetailSub;
import com.rh.i_mes_plus.service.sms.ISmsWmsReloadDocDetailSubService;
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
 * 调拨单日志操作明细表
 *
 * @author hbq
 * @date 2020-12-16 14:55:14
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "换料单日志操作明细表")
@RequestMapping("sms")
public class SmsWmsReloadDocDetailSubController {
    @Autowired
    private ISmsWmsReloadDocDetailSubService smsWmsReloadDocDetailSubService;
    
    @ApiOperation(value = "换料明细查询")
    @PostMapping("/smsWmsReloadDocDetailSub/listAll")
    public Result<PageResult> listAll(@RequestBody Map<String, Object> params) {
        Page<Map> list= smsWmsReloadDocDetailSubService.listAll(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    @ApiOperation(value = "换料汇总查询")
    @PostMapping("/smsWmsReloadDocDetailSub/listAllCollect")
    public Result<PageResult> listAllCollect(@RequestBody Map<String, Object> params) {
        Page<Map> list= smsWmsReloadDocDetailSubService.listAllCollect(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/smsWmsReloadDocDetailSub/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= smsWmsReloadDocDetailSubService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/smsWmsReloadDocDetailSub/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SmsWmsReloadDocDetailSub model = smsWmsReloadDocDetailSubService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/smsWmsReloadDocDetailSub/save")
    public Result save(@RequestBody SmsWmsReloadDocDetailSub smsWmsReloadDocDetailSub) {
        smsWmsReloadDocDetailSubService.saveOrUpdate(smsWmsReloadDocDetailSub);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/smsWmsReloadDocDetailSub/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<SmsWmsReloadDocDetailSub>> map) {
        List<SmsWmsReloadDocDetailSub> models = map.get("models");
        smsWmsReloadDocDetailSubService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/smsWmsReloadDocDetailSub/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        smsWmsReloadDocDetailSubService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/smsWmsReloadDocDetailSub/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SmsWmsReloadDocDetailSub> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SmsWmsReloadDocDetailSub.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        smsWmsReloadDocDetailSubService.save(u);
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
    @PostMapping("/smsWmsReloadDocDetailSub/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<SmsWmsReloadDocDetailSub> smsWmsReloadDocDetailSubs =new ArrayList<>();
        List<SmsWmsReloadDocDetailSub> smsWmsReloadDocDetailSubList = smsWmsReloadDocDetailSubService.list(new QueryWrapper<SmsWmsReloadDocDetailSub>().eq("cu_id", cuId));
        if (smsWmsReloadDocDetailSubList.isEmpty()) {smsWmsReloadDocDetailSubs.add(smsWmsReloadDocDetailSubService.getById(0)); } else {
            for (SmsWmsReloadDocDetailSub smsWmsReloadDocDetailSub : smsWmsReloadDocDetailSubList) {
                smsWmsReloadDocDetailSubs.add(smsWmsReloadDocDetailSub);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(smsWmsReloadDocDetailSubs, "调拨单日志操作明细表导出", "调拨单日志操作明细表导出", SmsWmsReloadDocDetailSub.class, "smsWmsReloadDocDetailSub.xls", response);

    }
}
