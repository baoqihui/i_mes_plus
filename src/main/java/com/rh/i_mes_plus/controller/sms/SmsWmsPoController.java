package com.rh.i_mes_plus.controller.sms;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sms.SmsWmsPo;
import com.rh.i_mes_plus.service.sms.ISmsWmsPoService;
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
 * 采购订单
 *
 * @author hqb
 * @date 2020-09-29 15:58:30
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "采购单")
@RequestMapping("sms")
public class SmsWmsPoController {
    @Autowired
    private ISmsWmsPoService smsWmsPoService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/smsWmsPo/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<SmsWmsPo> list= smsWmsPoService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/smsWmsPo/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SmsWmsPo model = smsWmsPoService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/smsWmsPo/save")
    public Result save(@RequestBody SmsWmsPo smsWmsPo) {
        smsWmsPoService.saveOrUpdate(smsWmsPo);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/smsWmsPo/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        smsWmsPoService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/smsWmsPo/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SmsWmsPo> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SmsWmsPo.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        smsWmsPoService.save(u);
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
    @PostMapping("/smsWmsPo/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<SmsWmsPo> smsWmsPos =new ArrayList<>();
        List<SmsWmsPo> smsWmsPoList = smsWmsPoService.list(new QueryWrapper<SmsWmsPo>().eq("cu_id", cuId));
        if (smsWmsPoList.isEmpty()) {smsWmsPos.add(smsWmsPoService.getById(0)); } else {
            for (SmsWmsPo smsWmsPo : smsWmsPoList) {
                smsWmsPos.add(smsWmsPo);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(smsWmsPos, "采购单导出", "采购单导出", SmsWmsPo.class, "smsWmsPo.xls", response);

    }
}
