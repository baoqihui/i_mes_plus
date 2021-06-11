package com.rh.i_mes_plus.controller.sms;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sms.SmsWmsOutStockPmItem;
import com.rh.i_mes_plus.service.sms.ISmsWmsOutStockPmItemService;
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
 * 工单和备料单绑定表
 *
 * @author hbq
 * @date 2020-11-02 17:19:51
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "工单和备料单绑定表")
@RequestMapping("sms")
public class SmsWmsOutStockPmItemController {
    @Autowired
    private ISmsWmsOutStockPmItemService smsWmsOutStockPmItemService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/smsWmsOutStockPmItem/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= smsWmsOutStockPmItemService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/smsWmsOutStockPmItem/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SmsWmsOutStockPmItem model = smsWmsOutStockPmItemService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/smsWmsOutStockPmItem/save")
    public Result save(@RequestBody SmsWmsOutStockPmItem smsWmsOutStockPmItem) {
        smsWmsOutStockPmItemService.saveOrUpdate(smsWmsOutStockPmItem);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/smsWmsOutStockPmItem/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<SmsWmsOutStockPmItem>> map) {
        List<SmsWmsOutStockPmItem> models = map.get("models");
        smsWmsOutStockPmItemService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/smsWmsOutStockPmItem/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        smsWmsOutStockPmItemService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/smsWmsOutStockPmItem/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SmsWmsOutStockPmItem> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SmsWmsOutStockPmItem.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        smsWmsOutStockPmItemService.save(u);
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
    @PostMapping("/smsWmsOutStockPmItem/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<SmsWmsOutStockPmItem> smsWmsOutStockPmItems =new ArrayList<>();
        List<SmsWmsOutStockPmItem> smsWmsOutStockPmItemList = smsWmsOutStockPmItemService.list(new QueryWrapper<SmsWmsOutStockPmItem>().eq("cu_id", cuId));
        if (smsWmsOutStockPmItemList.isEmpty()) {smsWmsOutStockPmItems.add(smsWmsOutStockPmItemService.getById(0)); } else {
            for (SmsWmsOutStockPmItem smsWmsOutStockPmItem : smsWmsOutStockPmItemList) {
                smsWmsOutStockPmItems.add(smsWmsOutStockPmItem);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(smsWmsOutStockPmItems, "工单和备料单绑定表导出", "工单和备料单绑定表导出", SmsWmsOutStockPmItem.class, "smsWmsOutStockPmItem.xls", response);

    }
}
