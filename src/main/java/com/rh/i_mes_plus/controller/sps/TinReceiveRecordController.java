package com.rh.i_mes_plus.controller.sps;
import java.io.IOException;
import java.util.List;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.rh.i_mes_plus.model.sps.TinStockInfo;
import com.rh.i_mes_plus.service.sps.ITinStockInfoService;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;


import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import com.rh.i_mes_plus.util.EasyPoiExcelUtil;
import com.rh.i_mes_plus.model.sps.TinReceiveRecord;
import com.rh.i_mes_plus.service.sps.ITinReceiveRecordService;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;

/**
 * 锡膏红胶入库表
 *
 * @author hbq
 * @date 2021-07-08 19:48:01
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "锡膏红胶入库表")
@RequestMapping("tin")
public class TinReceiveRecordController {
    @Autowired
    private ITinReceiveRecordService tinReceiveRecordService;
    @Autowired
    private ITinStockInfoService tinStockInfoService;

    /**
     * 根据类型代码查询要生成的入库单号
     */
    @ApiOperation(value = "根据类型代码查询要生成的入库单号")
    @PostMapping("/tinReceiveRecord/getDocNo")
    public Result getDocNo(@RequestBody Map<String,Object> map) {
        return tinReceiveRecordService.getDocNo(map);
    }

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/tinReceiveRecord/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= tinReceiveRecordService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/tinReceiveRecord/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        TinReceiveRecord model = tinReceiveRecordService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/tinReceiveRecord/save")
    public Result save(@RequestBody TinReceiveRecord tinReceiveRecord) {
        tinReceiveRecordService.saveOrUpdate(tinReceiveRecord);
        TinStockInfo tinStockInfo = new TinStockInfo();
        BeanUtil.copyProperties(tinReceiveRecord,tinStockInfo);
        tinStockInfoService.save(tinStockInfo);
        return Result.succeed("保存成功");
    }

    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/tinReceiveRecord/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<TinReceiveRecord>> map) {
        List<TinReceiveRecord> models = map.get("models");
        tinReceiveRecordService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/tinReceiveRecord/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        tinReceiveRecordService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/tinReceiveRecord/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<TinReceiveRecord> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, TinReceiveRecord.class);
            rowNum = list.size();
            if (rowNum > 0) {
                list.forEach(u -> {
                        tinReceiveRecordService.save(u);
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
    @PostMapping("/tinReceiveRecord/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<TinReceiveRecord> tinReceiveRecordList = ids==null||ids.isEmpty()? tinReceiveRecordService.list():(List)tinReceiveRecordService.listByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(tinReceiveRecordList, "锡膏红胶入库表导出", "锡膏红胶入库表导出", TinReceiveRecord.class, "tinReceiveRecord.xls", response);
    }
}
