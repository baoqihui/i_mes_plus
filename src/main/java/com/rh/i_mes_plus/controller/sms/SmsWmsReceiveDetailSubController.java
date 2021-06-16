package com.rh.i_mes_plus.controller.sms;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sms.SmsWmsReceiveDetailSub;
import com.rh.i_mes_plus.service.sms.ISmsWmsReceiveDetailSubService;
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
 * 
 *
 * @author hbq
 * @date 2020-11-06 16:04:22
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "入库条码表")
@RequestMapping("sms")
public class SmsWmsReceiveDetailSubController {
    @Autowired
    private ISmsWmsReceiveDetailSubService smsWmsReceiveDetailSubService;
    
    @ApiOperation(value = "入库明细查询")
    @PostMapping("/smsWmsReceiveDetailSub/listAll")
    public Result<PageResult> listAll(@RequestBody Map<String, Object> params) {
        Page<Map> list= smsWmsReceiveDetailSubService.listAll(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    @ApiOperation(value = "入库汇总查询")
    @PostMapping("/smsWmsReceiveDetailSub/listAllCollect")
    public Result<PageResult> listAllCollect(@RequestBody Map<String, Object> params) {
        Page<Map> list= smsWmsReceiveDetailSubService.listAllCollect(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/smsWmsReceiveDetailSub/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= smsWmsReceiveDetailSubService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/smsWmsReceiveDetailSub/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SmsWmsReceiveDetailSub model = smsWmsReceiveDetailSubService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/smsWmsReceiveDetailSub/save")
    public Result save(@RequestBody SmsWmsReceiveDetailSub smsWmsReceiveDetailSub) {
        smsWmsReceiveDetailSubService.saveOrUpdate(smsWmsReceiveDetailSub);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/smsWmsReceiveDetailSub/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<SmsWmsReceiveDetailSub>> map) {
        List<SmsWmsReceiveDetailSub> models = map.get("models");
        smsWmsReceiveDetailSubService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/smsWmsReceiveDetailSub/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        smsWmsReceiveDetailSubService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/smsWmsReceiveDetailSub/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SmsWmsReceiveDetailSub> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SmsWmsReceiveDetailSub.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        smsWmsReceiveDetailSubService.save(u);
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
    @PostMapping("/smsWmsReceiveDetailSub/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<SmsWmsReceiveDetailSub> smsWmsReceiveDetailSubs =new ArrayList<>();
        List<SmsWmsReceiveDetailSub> smsWmsReceiveDetailSubList = smsWmsReceiveDetailSubService.list(new QueryWrapper<SmsWmsReceiveDetailSub>().eq("cu_id", cuId));
        if (smsWmsReceiveDetailSubList.isEmpty()) {smsWmsReceiveDetailSubs.add(smsWmsReceiveDetailSubService.getById(0)); } else {
            for (SmsWmsReceiveDetailSub smsWmsReceiveDetailSub : smsWmsReceiveDetailSubList) {
                smsWmsReceiveDetailSubs.add(smsWmsReceiveDetailSub);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(smsWmsReceiveDetailSubs, "导出", "导出", SmsWmsReceiveDetailSub.class, "smsWmsReceiveDetailSub.xls", response);

    }
}
