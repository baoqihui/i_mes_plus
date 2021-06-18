package com.rh.i_mes_plus.controller.pdt;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.pdt.PdtWmsOutStockList;
import com.rh.i_mes_plus.service.pdt.IPdtWmsOutStockListService;
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
 * 成品出库明细表
 *
 * @author hbq
 * @date 2021-01-06 15:22:23
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "成品出库明细表")
@RequestMapping("pdt")
public class PdtWmsOutStockListController {
    @Autowired
    private IPdtWmsOutStockListService pdtWmsOutStockListService;

    @ApiOperation(value = "出库明细查询")
    @PostMapping("/smsWmsOutStockList/listAll")
    public Result<PageResult> listAll(@RequestBody Map<String, Object> params) {
        Page<Map> list= pdtWmsOutStockListService.listAll(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    @ApiOperation(value = "出库汇总查询")
    @PostMapping("/smsWmsOutStockList/listAllCollect")
    public Result<PageResult> listAllCollect(@RequestBody Map<String, Object> params) {
        Page<Map> list= pdtWmsOutStockListService.listAllCollect(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    @ApiOperation(value = "出库汇总查询")
    @PostMapping("/smsWmsOutStockList/listAllCollectByBoxNo")
    public Result<PageResult> listAllCollectByBoxNo(@RequestBody Map<String, Object> params) {
        Page<Map> list= pdtWmsOutStockListService.listAllCollectByBoxNo(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    /**
     * ecc生成
     */
    @ApiOperation(value = "ecc生成")
    @PostMapping("/pdtWmsOutStockList/ecc/{docNo}")
    public Result ecc(@PathVariable String docNo) {
        return pdtWmsOutStockListService.ecc(docNo);
    }
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/pdtWmsOutStockList/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= pdtWmsOutStockListService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/pdtWmsOutStockList/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        PdtWmsOutStockList model = pdtWmsOutStockListService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/pdtWmsOutStockList/save")
    public Result save(@RequestBody PdtWmsOutStockList pdtWmsOutStockList) {
        pdtWmsOutStockListService.saveOrUpdate(pdtWmsOutStockList);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/pdtWmsOutStockList/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<PdtWmsOutStockList>> map) {
        List<PdtWmsOutStockList> models = map.get("models");
        pdtWmsOutStockListService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/pdtWmsOutStockList/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        pdtWmsOutStockListService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/pdtWmsOutStockList/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<PdtWmsOutStockList> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, PdtWmsOutStockList.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        pdtWmsOutStockListService.save(u);
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
    @PostMapping("/pdtWmsOutStockList/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<PdtWmsOutStockList> pdtWmsOutStockLists =new ArrayList<>();
        List<PdtWmsOutStockList> pdtWmsOutStockListList = pdtWmsOutStockListService.list(new QueryWrapper<PdtWmsOutStockList>().eq("cu_id", cuId));
        if (pdtWmsOutStockListList.isEmpty()) {pdtWmsOutStockLists.add(pdtWmsOutStockListService.getById(0)); } else {
            for (PdtWmsOutStockList pdtWmsOutStockList : pdtWmsOutStockListList) {
                pdtWmsOutStockLists.add(pdtWmsOutStockList);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(pdtWmsOutStockLists, "成品出库明细表导出", "成品出库明细表导出", PdtWmsOutStockList.class, "pdtWmsOutStockList.xls", response);

    }
}
