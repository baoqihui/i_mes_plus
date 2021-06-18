package com.rh.i_mes_plus.controller.pdt;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.pdt.PdtWmsOutStockDetail;
import com.rh.i_mes_plus.service.pdt.IPdtWmsOutStockDetailService;
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
 * 成品出库详情表
 *
 * @author hbq
 * @date 2021-01-06 15:22:23
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "成品出库详情表")
@RequestMapping("pdt")
public class PdtWmsOutStockDetailController {
    @Autowired
    private IPdtWmsOutStockDetailService pdtWmsOutStockDetailService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/pdtWmsOutStockDetail/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= pdtWmsOutStockDetailService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/pdtWmsOutStockDetail/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        PdtWmsOutStockDetail model = pdtWmsOutStockDetailService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/pdtWmsOutStockDetail/save")
    public Result save(@RequestBody PdtWmsOutStockDetail pdtWmsOutStockDetail) {
        pdtWmsOutStockDetailService.saveOrUpdate(pdtWmsOutStockDetail);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/pdtWmsOutStockDetail/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<PdtWmsOutStockDetail>> map) {
        List<PdtWmsOutStockDetail> models = map.get("models");
        pdtWmsOutStockDetailService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/pdtWmsOutStockDetail/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        pdtWmsOutStockDetailService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/pdtWmsOutStockDetail/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<PdtWmsOutStockDetail> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, PdtWmsOutStockDetail.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        pdtWmsOutStockDetailService.save(u);
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
    @PostMapping("/pdtWmsOutStockDetail/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<PdtWmsOutStockDetail> pdtWmsOutStockDetails =new ArrayList<>();
        List<PdtWmsOutStockDetail> pdtWmsOutStockDetailList = pdtWmsOutStockDetailService.list(new QueryWrapper<PdtWmsOutStockDetail>().eq("cu_id", cuId));
        if (pdtWmsOutStockDetailList.isEmpty()) {pdtWmsOutStockDetails.add(pdtWmsOutStockDetailService.getById(0)); } else {
            for (PdtWmsOutStockDetail pdtWmsOutStockDetail : pdtWmsOutStockDetailList) {
                pdtWmsOutStockDetails.add(pdtWmsOutStockDetail);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(pdtWmsOutStockDetails, "成品出库详情表导出", "成品出库详情表导出", PdtWmsOutStockDetail.class, "pdtWmsOutStockDetail.xls", response);

    }
}
