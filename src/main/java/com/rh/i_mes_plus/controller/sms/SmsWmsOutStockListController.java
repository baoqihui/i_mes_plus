package com.rh.i_mes_plus.controller.sms;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sms.SmsWmsOutStockList;
import com.rh.i_mes_plus.service.sms.ISmsWmsOutStockListService;
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
 * 出库明细表
 *
 * @author hbq
 * @date 2020-11-02 14:32:34
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "出库明细表")
@RequestMapping("sms")
public class SmsWmsOutStockListController {
    @Autowired
    private ISmsWmsOutStockListService smsWmsOutStockListService;
    
    @ApiOperation(value = "出库明细查询")
    @PostMapping("/smsWmsOutStockList/listAll")
    public Result<PageResult> listAll(@RequestBody Map<String, Object> params) {
        Page<Map> list= smsWmsOutStockListService.listAll(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    @ApiOperation(value = "出库汇总查询")
    @PostMapping("/smsWmsOutStockList/listAllCollect")
    public Result<PageResult> listAllCollect(@RequestBody Map<String, Object> params) {
        Page<Map> list= smsWmsOutStockListService.listAllCollect(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/smsWmsOutStockList/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= smsWmsOutStockListService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/smsWmsOutStockList/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SmsWmsOutStockList model = smsWmsOutStockListService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/smsWmsOutStockList/save")
    public Result save(@RequestBody SmsWmsOutStockList smsWmsOutStockList) {
        smsWmsOutStockListService.saveOrUpdate(smsWmsOutStockList);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/smsWmsOutStockList/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<SmsWmsOutStockList>> map) {
        List<SmsWmsOutStockList> models = map.get("models");
        smsWmsOutStockListService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/smsWmsOutStockList/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        smsWmsOutStockListService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/smsWmsOutStockList/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SmsWmsOutStockList> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SmsWmsOutStockList.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        smsWmsOutStockListService.save(u);
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
    @PostMapping("/smsWmsOutStockList/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<SmsWmsOutStockList> smsWmsOutStockLists =new ArrayList<>();
        List<SmsWmsOutStockList> smsWmsOutStockListList = smsWmsOutStockListService.list(new QueryWrapper<SmsWmsOutStockList>().eq("cu_id", cuId));
        if (smsWmsOutStockListList.isEmpty()) {smsWmsOutStockLists.add(smsWmsOutStockListService.getById(0)); } else {
            for (SmsWmsOutStockList smsWmsOutStockList : smsWmsOutStockListList) {
                smsWmsOutStockLists.add(smsWmsOutStockList);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(smsWmsOutStockLists, "出库明细表导出", "出库明细表导出", SmsWmsOutStockList.class, "smsWmsOutStockList.xls", response);

    }
}
