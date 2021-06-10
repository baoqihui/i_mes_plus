package com.rh.i_mes_plus.controller.gtoa;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.gtoa.WmsWorkTracingMain;
import com.rh.i_mes_plus.model.gtoa.WmsWorkTrackingDetail;
import com.rh.i_mes_plus.service.gtoa.IWmsWorkTracingMainService;
import com.rh.i_mes_plus.service.gtoa.IWmsWorkTrackingDetailService;
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
 * @date 2020-12-02 15:59:56
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "任务管理表")
@RequestMapping("wms")
public class WmsWorkTracingMainController {
    @Autowired
    private IWmsWorkTracingMainService wmsWorkTracingMainService;
    @Autowired
    private IWmsWorkTrackingDetailService wmsWorkTrackingDetailService;
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/wmsWorkTracingMain/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= wmsWorkTracingMainService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/wmsWorkTracingMain/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        WmsWorkTracingMain model = wmsWorkTracingMainService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增
     */
    @ApiOperation(value = "新增")
    @PostMapping("/wmsWorkTracingMain/save")
    public Result save(@RequestBody WmsWorkTracingMain wmsWorkTracingMain) {
        wmsWorkTracingMainService.saveOrUpdate(wmsWorkTracingMain);
        return Result.succeed("保存成功");
    }
    
    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/wmsWorkTracingMain/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<WmsWorkTracingMain>> map) {
        List<WmsWorkTracingMain> models = map.get("models");
        wmsWorkTracingMainService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/wmsWorkTracingMain/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        wmsWorkTracingMainService.removeByIds(ids);
        for (Long id : ids) {
            wmsWorkTrackingDetailService.remove(new QueryWrapper<WmsWorkTrackingDetail>().eq("work_id",id));
        }
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/wmsWorkTracingMain/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<WmsWorkTracingMain> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, WmsWorkTracingMain.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        wmsWorkTracingMainService.save(u);
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
    @PostMapping("/wmsWorkTracingMain/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<WmsWorkTracingMain> wmsWorkTracingMains =new ArrayList<>();
        List<WmsWorkTracingMain> wmsWorkTracingMainList = wmsWorkTracingMainService.list(new QueryWrapper<WmsWorkTracingMain>().eq("cu_id", cuId));
        if (wmsWorkTracingMainList.isEmpty()) {wmsWorkTracingMains.add(wmsWorkTracingMainService.getById(0)); } else {
            for (WmsWorkTracingMain wmsWorkTracingMain : wmsWorkTracingMainList) {
                wmsWorkTracingMains.add(wmsWorkTracingMain);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(wmsWorkTracingMains, "任务管理表导出", "任务管理表导出", WmsWorkTracingMain.class, "wmsWorkTracingMain.xls", response);

    }
}
