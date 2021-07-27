package com.rh.i_mes_plus.controller.sms;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sms.SmsWmsStockInfo;
import com.rh.i_mes_plus.service.sms.ISmsWmsStockInfoService;
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
 * 库存信息表
 *
 * @author hbq
 * @date 2020-12-09 10:11:55
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "库存信息表")
@RequestMapping("sms")
public class SmsWmsStockInfoController {
    @Autowired
    private ISmsWmsStockInfoService smsWmsStockInfoService;
    /**
     * 库存明细查询
     */
    @ApiOperation(value = "库存明细查询")
    @PostMapping("/smsWmsStockInfo/listAll")
    public Result<PageResult> listAll(@RequestBody Map<String, Object> params) {
        Page<Map> list= smsWmsStockInfoService.listAll(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    /**
     * 库存汇总查询
     */
    @ApiOperation(value = "库存汇总查询")
    @PostMapping("/smsWmsStockInfo/listAllCollect")
    public Result<PageResult> listAllCollect(@RequestBody Map<String, Object> params) {
        Page<Map> list= smsWmsStockInfoService.listAllCollect(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/smsWmsStockInfo/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= smsWmsStockInfoService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/smsWmsStockInfo/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SmsWmsStockInfo model = smsWmsStockInfoService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 根据条码查库位
     */
    @ApiOperation(value = "根据条码查库位")
    @PostMapping("/smsWmsStockInfo/getLocation")
    public Result getLocation(@RequestBody Map<String, Object> params) {
        return smsWmsStockInfoService.getLocation(params);
    }

    /**
     * 上架
     */
    @ApiOperation(value = "上架")
    @PostMapping("/mobile/putAway")
    public Result putAway(@RequestBody Map<String, Object> params) {
        return smsWmsStockInfoService.putAway(params);
    }
    /**
     * 下架
     */
    @ApiOperation(value = "下架")
    @PostMapping("/mobile/soldOut")
    public Result soldOut(@RequestBody Map<String, Object> params) {
        return smsWmsStockInfoService.soldOut(params);
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/smsWmsStockInfo/save")
    public Result save(@RequestBody SmsWmsStockInfo smsWmsStockInfo) {
        smsWmsStockInfoService.saveOrUpdate(smsWmsStockInfo);
        return Result.succeed("保存成功");
    }
 
    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/smsWmsStockInfo/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<SmsWmsStockInfo>> map) {
        List<SmsWmsStockInfo> models = map.get("models");
        smsWmsStockInfoService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/smsWmsStockInfo/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        smsWmsStockInfoService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/smsWmsStockInfo/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SmsWmsStockInfo> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SmsWmsStockInfo.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        smsWmsStockInfoService.save(u);
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
    @PostMapping("/smsWmsStockInfo/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<SmsWmsStockInfo> smsWmsStockInfos =new ArrayList<>();
        List<SmsWmsStockInfo> smsWmsStockInfoList = smsWmsStockInfoService.list(new QueryWrapper<SmsWmsStockInfo>().eq("cu_id", cuId));
        if (smsWmsStockInfoList.isEmpty()) {smsWmsStockInfos.add(smsWmsStockInfoService.getById(0)); } else {
            for (SmsWmsStockInfo smsWmsStockInfo : smsWmsStockInfoList) {
                smsWmsStockInfos.add(smsWmsStockInfo);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(smsWmsStockInfos, "库存信息表(修改此表：如果在库数量为0时，需要把相关信息保存至表T_WMS_STOCK_ZERO_INFO，退料时才能得到SN原有的相关信息)导出", "库存信息表(修改此表：如果在库数量为0时，需要把相关信息保存至表T_WMS_STOCK_ZERO_INFO，退料时才能得到SN原有的相关信息)导出", SmsWmsStockInfo.class, "smsWmsStockInfo.xls", response);

    }
}
