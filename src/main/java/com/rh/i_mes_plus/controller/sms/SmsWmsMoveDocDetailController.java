package com.rh.i_mes_plus.controller.sms;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sms.SmsWmsMoveDocDetail;
import com.rh.i_mes_plus.service.sms.ISmsWmsMoveDocDetailService;
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
 * @date 2020-12-11 13:39:17
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "调拨单物料明细表")
@RequestMapping("sms")
public class SmsWmsMoveDocDetailController {
    @Autowired
    private ISmsWmsMoveDocDetailService smsWmsMoveDocDetailService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/smsWmsMoveDocDetail/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= smsWmsMoveDocDetailService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/smsWmsMoveDocDetail/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SmsWmsMoveDocDetail model = smsWmsMoveDocDetailService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/smsWmsMoveDocDetail/save")
    public Result save(@RequestBody SmsWmsMoveDocDetail smsWmsMoveDocDetail) {
        smsWmsMoveDocDetailService.saveOrUpdate(smsWmsMoveDocDetail);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/smsWmsMoveDocDetail/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<SmsWmsMoveDocDetail>> map) {
        List<SmsWmsMoveDocDetail> models = map.get("models");
        smsWmsMoveDocDetailService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/smsWmsMoveDocDetail/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        smsWmsMoveDocDetailService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/smsWmsMoveDocDetail/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SmsWmsMoveDocDetail> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SmsWmsMoveDocDetail.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        smsWmsMoveDocDetailService.save(u);
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
    @PostMapping("/smsWmsMoveDocDetail/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<SmsWmsMoveDocDetail> smsWmsMoveDocDetails =new ArrayList<>();
        List<SmsWmsMoveDocDetail> smsWmsMoveDocDetailList = smsWmsMoveDocDetailService.list(new QueryWrapper<SmsWmsMoveDocDetail>().eq("cu_id", cuId));
        if (smsWmsMoveDocDetailList.isEmpty()) {smsWmsMoveDocDetails.add(smsWmsMoveDocDetailService.getById(0)); } else {
            for (SmsWmsMoveDocDetail smsWmsMoveDocDetail : smsWmsMoveDocDetailList) {
                smsWmsMoveDocDetails.add(smsWmsMoveDocDetail);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(smsWmsMoveDocDetails, "调拨单物料明细表导出", "调拨单物料明细表导出", SmsWmsMoveDocDetail.class, "smsWmsMoveDocDetail.xls", response);

    }
}
