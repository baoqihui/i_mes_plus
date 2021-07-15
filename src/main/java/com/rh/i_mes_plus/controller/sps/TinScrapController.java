package com.rh.i_mes_plus.controller.sps;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.rh.i_mes_plus.common.model.SysConst;
import com.rh.i_mes_plus.model.sps.TinLog;
import com.rh.i_mes_plus.model.sps.TinStockInfo;
import com.rh.i_mes_plus.model.sps.TinUseRecord;
import com.rh.i_mes_plus.service.sps.ITinLogService;
import com.rh.i_mes_plus.service.sps.ITinStockInfoService;
import com.rh.i_mes_plus.service.sps.ITinUseRecordService;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;


import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import com.rh.i_mes_plus.util.EasyPoiExcelUtil;
import com.rh.i_mes_plus.model.sps.TinScrap;
import com.rh.i_mes_plus.service.sps.ITinScrapService;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;

/**
 * 红锡膏报废
 *
 * @author hbq
 * @date 2021-07-08 19:48:01
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "红锡膏报废")
@RequestMapping("tin")
public class TinScrapController {
    @Autowired
    private ITinScrapService tinScrapService;
    @Autowired
    private ITinStockInfoService tinStockInfoService;
    @Autowired
    private ITinUseRecordService tinUseRecordService;
    @Autowired
    private ITinLogService tinLogService;
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/tinScrap/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= tinScrapService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 汇总
     */
    @ApiOperation(value = "汇总")
    @PostMapping("/tinScrap/total")
    public Result<PageResult> total(@RequestBody Map<String, Object> params) {
        Page<Map> list= tinScrapService.findListTotal(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/tinScrap/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        TinScrap model = tinScrapService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/tinScrap/save")
    public Result save(@RequestBody TinScrap tinScrap) {
        tinScrapService.saveOrUpdate(tinScrap);
        TinStockInfo stockInfo = tinStockInfoService.getOne(new LambdaQueryWrapper<TinStockInfo>()
                .eq(TinStockInfo::getTinSn, tinScrap.getTinSn())
        );
        stockInfo.setStatus(SysConst.TIN_STATUS.BF);
        tinStockInfoService.updateById(stockInfo);
        //添加日志
        TinLog tinLog=TinLog.builder()
                .tinSn(stockInfo.getTinSn())
                .itemCode(stockInfo.getItemCode())
                .manufactureDate(stockInfo.getManufactureDate())
                .lotNo(stockInfo.getLotNo())
                .content("操作人："+tinScrap.getScrapName()+" 在"+new Date()+" 报废 操作")
                .build();
        tinLogService.save(tinLog);
        tinUseRecordService.update(new LambdaUpdateWrapper<TinUseRecord>()
                .eq(TinUseRecord::getTinSn,tinScrap.getTinSn())
                .set(TinUseRecord::getUseingFlag,2)
        );
        return Result.succeed("保存成功");
    }

    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/tinScrap/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<TinScrap>> map) {
        List<TinScrap> models = map.get("models");
        tinScrapService.saveOrUpdateBatch(models);
        for (TinScrap tinScrap : models) {
            TinStockInfo stockInfo = tinStockInfoService.getOne(new LambdaQueryWrapper<TinStockInfo>()
                    .eq(TinStockInfo::getTinSn, tinScrap.getTinSn())
            );
            stockInfo.setStatus(SysConst.TIN_STATUS.BF);
            tinStockInfoService.updateById(stockInfo);
            //添加日志
            TinLog tinLog=TinLog.builder()
                    .tinSn(stockInfo.getTinSn())
                    .itemCode(stockInfo.getItemCode())
                    .manufactureDate(stockInfo.getManufactureDate())
                    .lotNo(stockInfo.getLotNo())
                    .content("操作人："+tinScrap.getScrapName()+" 在"+new Date()+" 报废 操作")
                    .build();
            tinLogService.save(tinLog);
            tinUseRecordService.update(new LambdaUpdateWrapper<TinUseRecord>()
                    .eq(TinUseRecord::getTinSn,tinScrap.getTinSn())
                    .set(TinUseRecord::getUseingFlag,2)
            );
        }
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/tinScrap/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        Collection<TinScrap> tinScraps = tinScrapService.listByIds(ids);
        for (TinScrap tinScrap : tinScraps) {
            tinStockInfoService.update(new LambdaUpdateWrapper<TinStockInfo>()
                    .eq(TinStockInfo::getTinSn,tinScrap.getTinSn())
                    .set(TinStockInfo::getStatus, SysConst.TIN_STATUS.LY)
            );
            tinUseRecordService.update(new LambdaUpdateWrapper<TinUseRecord>()
                    .eq(TinUseRecord::getTinSn,tinScrap.getTinSn())
                    .set(TinUseRecord::getUseingFlag,1)
            );
        }
        tinScrapService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/tinScrap/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<TinScrap> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, TinScrap.class);
            rowNum = list.size();
            if (rowNum > 0) {
                list.forEach(u -> {
                        tinScrapService.save(u);
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
    @PostMapping("/tinScrap/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<TinScrap> tinScrapList = ids==null||ids.isEmpty()? tinScrapService.list():(List)tinScrapService.listByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(tinScrapList, "红锡膏报废导出", "红锡膏报废导出", TinScrap.class, "tinScrap.xls", response);
    }
}
