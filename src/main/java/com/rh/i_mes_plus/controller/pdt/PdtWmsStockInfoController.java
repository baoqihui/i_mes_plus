package com.rh.i_mes_plus.controller.pdt;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.pdt.PdtWmsStockInfo;
import com.rh.i_mes_plus.model.ums.UmsItemMes;
import com.rh.i_mes_plus.service.pdt.IPdtWmsStockInfoService;
import com.rh.i_mes_plus.service.ums.IUmsDepaService;
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
 * @date 2020-12-28 14:29:31
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "成品库存信息表")
@RequestMapping("pdt")
public class PdtWmsStockInfoController {
    @Autowired
    private IPdtWmsStockInfoService pdtWmsStockInfoService;
    @Autowired
    private IUmsDepaService umsDepaService;
    /**
     * 库存明细查询
     */
    @ApiOperation(value = "库存明细查询")
    @PostMapping("/pdtWmsStockInfo/listAll")
    public Result<PageResult> listAll(@RequestBody Map<String, Object> params) {
        Page<Map> list= pdtWmsStockInfoService.listAll(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    /**
     * 库存汇总查询
     */
    @ApiOperation(value = "库存汇总查询")
    @PostMapping("/pdtWmsStockInfo/listAllCollect")
    public Result<PageResult> listAllCollect(@RequestBody Map<String, Object> params) {
        Page<Map> list= pdtWmsStockInfoService.listAllCollect(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    /**
     * 库存箱码汇总查询
     */
    @ApiOperation(value = "库存箱码汇总查询")
    @PostMapping("/pdtWmsStockInfo/listAllCollectByBoxNo")
    public Result<PageResult> listAllCollectByBoxNo(@RequestBody Map<String, Object> params) {
        Page<Map> list= pdtWmsStockInfoService.listAllCollectByBoxNo(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/pdtWmsStockInfo/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= pdtWmsStockInfoService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    /**
     * 列表
     */
    @ApiOperation(value = "查询sap物料列表")
    @PostMapping("/pdtWmsStockInfo/listOnlySap")
    public Result<PageResult> listOnlySap(@RequestBody Map<String, Object> params) {
        String code = MapUtil.getStr(params, "depaCode");
        List<String> umsDepas = StrUtil.isNotEmpty(code)?umsDepaService.getSon(params):new ArrayList<>();
        params.put("umsDepas",umsDepas);
        Page<UmsItemMes> list= pdtWmsStockInfoService.findList2(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/pdtWmsStockInfo/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        PdtWmsStockInfo model = pdtWmsStockInfoService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/pdtWmsStockInfo/save")
    public Result save(@RequestBody PdtWmsStockInfo pdtWmsStockInfo) {
        pdtWmsStockInfoService.saveOrUpdate(pdtWmsStockInfo);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/pdtWmsStockInfo/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<PdtWmsStockInfo>> map) {
        List<PdtWmsStockInfo> models = map.get("models");
        pdtWmsStockInfoService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/pdtWmsStockInfo/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        pdtWmsStockInfoService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/pdtWmsStockInfo/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<PdtWmsStockInfo> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, PdtWmsStockInfo.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        pdtWmsStockInfoService.save(u);
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
    @PostMapping("/pdtWmsStockInfo/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<PdtWmsStockInfo> pdtWmsStockInfos =new ArrayList<>();
        List<PdtWmsStockInfo> pdtWmsStockInfoList = pdtWmsStockInfoService.list(new QueryWrapper<PdtWmsStockInfo>().eq("cu_id", cuId));
        if (pdtWmsStockInfoList.isEmpty()) {pdtWmsStockInfos.add(pdtWmsStockInfoService.getById(0)); } else {
            for (PdtWmsStockInfo pdtWmsStockInfo : pdtWmsStockInfoList) {
                pdtWmsStockInfos.add(pdtWmsStockInfo);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(pdtWmsStockInfos, "库存信息表(修改此表：如果在库数量为0时，需要把相关信息保存至表T_WMS_STOCK_ZERO_INFO，退料时才能得到SN原有的相关信息)导出", "库存信息表(修改此表：如果在库数量为0时，需要把相关信息保存至表T_WMS_STOCK_ZERO_INFO，退料时才能得到SN原有的相关信息)导出", PdtWmsStockInfo.class, "pdtWmsStockInfo.xls", response);

    }
}
