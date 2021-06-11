package com.rh.i_mes_plus.controller.ums;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.ums.UmsItemMes;
import com.rh.i_mes_plus.model.ums.UmsItemSap;
import com.rh.i_mes_plus.service.ums.IUmsItemSapService;
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
 * 物料信息（sap获取）
 *
 * @author hqb
 * @date 2020-09-21 11:13:27
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "物料信息（sap获取）")
@RequestMapping("ums")
public class UmsItemSapController {
    @Autowired
    private IUmsItemSapService umsItemSapService;
    @Autowired
    private IUmsDepaService umsDepaService;
    /**
     * 列表
     */
    @ApiOperation(value = "查询sap和mes共同物料列表")
    @PostMapping("/umsItemSap/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        String code = MapUtil.getStr(params, "depaCode");
        List<String> umsDepas = StrUtil.isNotEmpty(code)?umsDepaService.getSon(params):new ArrayList<>();
        params.put("umsDepas",umsDepas);
        Page<Map> list= umsItemSapService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    /**
     * 列表
     */
    @ApiOperation(value = "查询sap物料列表")
    @PostMapping("/umsItemSap/listOnlySap")
    public Result<PageResult> listOnlySap(@RequestBody Map<String, Object> params) {
        String code = MapUtil.getStr(params, "depaCode");
        List<String> umsDepas = StrUtil.isNotEmpty(code)?umsDepaService.getSon(params):new ArrayList<>();
        params.put("umsDepas",umsDepas);
        Page<UmsItemMes> list= umsItemSapService.findList2(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/umsItemSap/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        UmsItemSap model = umsItemSapService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * sap同步物料主数据
     */
    @ApiOperation(value = "sap同步物料主数据")
    @PostMapping("/umsItemSap/save")
    public Result save(@RequestBody List<UmsItemSap> umsItemSaps) {
        return umsItemSapService.saveAll(umsItemSaps);
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/umsItemSap/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        umsItemSapService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/umsItemSap/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<UmsItemSap> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, UmsItemSap.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        umsItemSapService.save(u);
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
    @PostMapping("/umsItemSap/leadOut")
    public void leadOut(HttpServletResponse response) throws IOException {
        List<UmsItemSap> umsItemSaps =new ArrayList<>();
        List<UmsItemSap> umsItemSapList = umsItemSapService.list(new QueryWrapper<>());
        if (umsItemSapList.isEmpty()) {umsItemSaps.add(umsItemSapService.getById(0)); } else {
            for (UmsItemSap umsItemSap : umsItemSapList) {
                umsItemSaps.add(umsItemSap);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(umsItemSaps, "物料信息（sap获取）导出", "物料信息（sap获取）导出", UmsItemSap.class, "umsItemSap.xls", response);

    }
}
