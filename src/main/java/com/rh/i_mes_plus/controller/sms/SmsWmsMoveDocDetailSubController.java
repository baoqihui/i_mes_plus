package com.rh.i_mes_plus.controller.sms;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sms.SmsWmsMoveDocDetailSub;
import com.rh.i_mes_plus.service.sms.ISmsWmsMoveDocDetailSubService;
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
 * @date 2020-12-11 13:39:17
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "调拨单日志操作明细表")
@RequestMapping("sms")
public class SmsWmsMoveDocDetailSubController {
    @Autowired
    private ISmsWmsMoveDocDetailSubService smsWmsMoveDocDetailSubService;
    @ApiOperation(value = "调拨明细查询")
    @PostMapping("/smsWmsMoveDocDetailSub/listAll")
    public Result<PageResult> listAll(@RequestBody Map<String, Object> params) {
        Page<Map> list= smsWmsMoveDocDetailSubService.listAll(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    @ApiOperation(value = "调拨汇总查询")
    @PostMapping("/smsWmsMoveDocDetailSub/listAllCollect")
    public Result<PageResult> listAllCollect(@RequestBody Map<String, Object> params) {
        Page<Map> list= smsWmsMoveDocDetailSubService.listAllCollect(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/smsWmsMoveDocDetailSub/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= smsWmsMoveDocDetailSubService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/smsWmsMoveDocDetailSub/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SmsWmsMoveDocDetailSub model = smsWmsMoveDocDetailSubService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/smsWmsMoveDocDetailSub/save")
    public Result save(@RequestBody SmsWmsMoveDocDetailSub smsWmsMoveDocDetailSub) {
        smsWmsMoveDocDetailSubService.saveOrUpdate(smsWmsMoveDocDetailSub);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/smsWmsMoveDocDetailSub/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<SmsWmsMoveDocDetailSub>> map) {
        List<SmsWmsMoveDocDetailSub> models = map.get("models");
        smsWmsMoveDocDetailSubService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/smsWmsMoveDocDetailSub/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        smsWmsMoveDocDetailSubService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/smsWmsMoveDocDetailSub/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SmsWmsMoveDocDetailSub> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SmsWmsMoveDocDetailSub.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        smsWmsMoveDocDetailSubService.save(u);
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
    @PostMapping("/smsWmsMoveDocDetailSub/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<SmsWmsMoveDocDetailSub> smsWmsMoveDocDetailSubs =new ArrayList<>();
        List<SmsWmsMoveDocDetailSub> smsWmsMoveDocDetailSubList = smsWmsMoveDocDetailSubService.list(new QueryWrapper<SmsWmsMoveDocDetailSub>().eq("cu_id", cuId));
        if (smsWmsMoveDocDetailSubList.isEmpty()) {smsWmsMoveDocDetailSubs.add(smsWmsMoveDocDetailSubService.getById(0)); } else {
            for (SmsWmsMoveDocDetailSub smsWmsMoveDocDetailSub : smsWmsMoveDocDetailSubList) {
                smsWmsMoveDocDetailSubs.add(smsWmsMoveDocDetailSub);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(smsWmsMoveDocDetailSubs, "调拨单日志操作明细表导出", "调拨单日志操作明细表导出", SmsWmsMoveDocDetailSub.class, "smsWmsMoveDocDetailSub.xls", response);

    }
}
