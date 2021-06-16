package com.rh.i_mes_plus.controller.sms;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sms.SmsWmsOutStockDetail;
import com.rh.i_mes_plus.service.sms.ISmsWmsOutStockDetailService;
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
 * 出库物料表
 *
 * @author hbq
 * @date 2020-11-02 14:32:34
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "出库物料表")
@RequestMapping("sms")
public class SmsWmsOutStockDetailController {
    @Autowired
    private ISmsWmsOutStockDetailService smsWmsOutStockDetailService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/smsWmsOutStockDetail/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= smsWmsOutStockDetailService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/smsWmsOutStockDetail/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SmsWmsOutStockDetail model = smsWmsOutStockDetailService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/smsWmsOutStockDetail/save")
    public Result save(@RequestBody SmsWmsOutStockDetail smsWmsOutStockDetail) {
        smsWmsOutStockDetailService.saveOrUpdate(smsWmsOutStockDetail);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/smsWmsOutStockDetail/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<SmsWmsOutStockDetail>> map) {
        List<SmsWmsOutStockDetail> models = map.get("models");
        smsWmsOutStockDetailService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/smsWmsOutStockDetail/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        smsWmsOutStockDetailService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/smsWmsOutStockDetail/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SmsWmsOutStockDetail> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SmsWmsOutStockDetail.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        smsWmsOutStockDetailService.save(u);
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
    @PostMapping("/smsWmsOutStockDetail/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<SmsWmsOutStockDetail> smsWmsOutStockDetails =new ArrayList<>();
        List<SmsWmsOutStockDetail> smsWmsOutStockDetailList = smsWmsOutStockDetailService.list(new QueryWrapper<SmsWmsOutStockDetail>().eq("cu_id", cuId));
        if (smsWmsOutStockDetailList.isEmpty()) {smsWmsOutStockDetails.add(smsWmsOutStockDetailService.getById(0)); } else {
            for (SmsWmsOutStockDetail smsWmsOutStockDetail : smsWmsOutStockDetailList) {
                smsWmsOutStockDetails.add(smsWmsOutStockDetail);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(smsWmsOutStockDetails, "出库物料表导出", "出库物料表导出", SmsWmsOutStockDetail.class, "smsWmsOutStockDetail.xls", response);

    }
}
