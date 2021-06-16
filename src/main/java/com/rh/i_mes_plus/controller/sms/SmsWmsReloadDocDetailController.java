package com.rh.i_mes_plus.controller.sms;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sms.SmsWmsReloadDocDetail;
import com.rh.i_mes_plus.service.sms.ISmsWmsReloadDocDetailService;
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
 * 调拨单物料明细表
 *
 * @author hbq
 * @date 2020-12-16 14:55:14
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "换料单物料明细表")
@RequestMapping("sms")
public class SmsWmsReloadDocDetailController {
    @Autowired
    private ISmsWmsReloadDocDetailService smsWmsReloadDocDetailService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/smsWmsReloadDocDetail/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= smsWmsReloadDocDetailService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/smsWmsReloadDocDetail/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SmsWmsReloadDocDetail model = smsWmsReloadDocDetailService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/smsWmsReloadDocDetail/save")
    public Result save(@RequestBody SmsWmsReloadDocDetail smsWmsReloadDocDetail) {
        smsWmsReloadDocDetailService.saveOrUpdate(smsWmsReloadDocDetail);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/smsWmsReloadDocDetail/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<SmsWmsReloadDocDetail>> map) {
        List<SmsWmsReloadDocDetail> models = map.get("models");
        smsWmsReloadDocDetailService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/smsWmsReloadDocDetail/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        smsWmsReloadDocDetailService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/smsWmsReloadDocDetail/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SmsWmsReloadDocDetail> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SmsWmsReloadDocDetail.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        smsWmsReloadDocDetailService.save(u);
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
    @PostMapping("/smsWmsReloadDocDetail/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<SmsWmsReloadDocDetail> smsWmsReloadDocDetails =new ArrayList<>();
        List<SmsWmsReloadDocDetail> smsWmsReloadDocDetailList = smsWmsReloadDocDetailService.list(new QueryWrapper<SmsWmsReloadDocDetail>().eq("cu_id", cuId));
        if (smsWmsReloadDocDetailList.isEmpty()) {smsWmsReloadDocDetails.add(smsWmsReloadDocDetailService.getById(0)); } else {
            for (SmsWmsReloadDocDetail smsWmsReloadDocDetail : smsWmsReloadDocDetailList) {
                smsWmsReloadDocDetails.add(smsWmsReloadDocDetail);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(smsWmsReloadDocDetails, "调拨单物料明细表导出", "调拨单物料明细表导出", SmsWmsReloadDocDetail.class, "smsWmsReloadDocDetail.xls", response);

    }
}
