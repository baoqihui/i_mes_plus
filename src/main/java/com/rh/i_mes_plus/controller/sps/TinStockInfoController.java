package com.rh.i_mes_plus.controller.sps;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.rh.i_mes_plus.model.sps.TinLog;
import com.rh.i_mes_plus.service.sps.ITinLogService;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;


import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import com.rh.i_mes_plus.util.EasyPoiExcelUtil;
import com.rh.i_mes_plus.model.sps.TinStockInfo;
import com.rh.i_mes_plus.service.sps.ITinStockInfoService;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;

/**
 * 锡膏红胶库存表
 *
 * @author hbq
 * @date 2021-07-08 19:48:01
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "锡膏红胶库存表")
@RequestMapping("tin")
public class TinStockInfoController {
    @Autowired
    private ITinStockInfoService tinStockInfoService;
    @Autowired
    private ITinLogService tinLogService;
    /**
     * 根据类型代码查询要生成的入库单号
     */
    @ApiOperation(value = "根据类型代码查询要生成的入库单号")
    @PostMapping("/tinStockInfo/getDocNo")
    public Result getDocNo(@RequestBody Map<String,Object> map) {
        return tinStockInfoService.getDocNo(map);
    }

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/tinStockInfo/listTotal")
    public Result<PageResult> listTotal(@RequestBody Map<String, Object> params) {
        Page<Map> list= tinStockInfoService.findListTotal(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/tinStockInfo/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= tinStockInfoService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    /**
     * 生产追溯
     */
    @ApiOperation(value = "生产追溯")
    @PostMapping("/tinStockInfo/listAll")
    public Result<PageResult> listAll(@RequestBody Map<String, Object> params) {
        Page<Map> list= tinStockInfoService.findListAll(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/tinStockInfo/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        TinStockInfo model = tinStockInfoService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/tinStockInfo/save")
    public Result save(@RequestBody TinStockInfo tinStockInfo) {
        tinStockInfoService.saveOrUpdate(tinStockInfo);
        return Result.succeed("保存成功");
    }

    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/tinStockInfo/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<TinStockInfo>> map) {
        List<TinStockInfo> models = map.get("models");
        for (TinStockInfo stockInfo : models) {
            if (stockInfo.getId()==null){
                //添加日志
                TinLog tinLog=TinLog.builder()
                        .tinSn(stockInfo.getTinSn())
                        .itemCode(stockInfo.getItemCode())
                        .manufactureDate(stockInfo.getManufactureDate())
                        .lotNo(stockInfo.getLotNo())
                        .content("操作人："+stockInfo.getReceiveName()+" 在"+ DateUtil.now()+" 入库 操作")
                        .build();
                tinLogService.save(tinLog);
            }
        }
        tinStockInfoService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/tinStockInfo/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        tinStockInfoService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/tinStockInfo/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<TinStockInfo> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, TinStockInfo.class);
            rowNum = list.size();
            if (rowNum > 0) {
                list.forEach(u -> {
                        tinStockInfoService.save(u);
                });
                return Result.succeed("成功导入信息"+rowNum+"行数据");
            }
        }
        return Result.failed("导入失败");
    }
    
    /**
     * 导出（传入ids数组，选择指定id）
     */
    @ApiOperation(value = "导出（传入ids数组，选择指定id）")
    @PostMapping("/tinStockInfo/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<TinStockInfo> tinStockInfoList = ids==null||ids.isEmpty()? tinStockInfoService.list():(List)tinStockInfoService.listByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(tinStockInfoList, "锡膏红胶库存表导出", "锡膏红胶库存表导出", TinStockInfo.class, "tinStockInfo.xls", response);
    }
}
