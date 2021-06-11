package com.rh.i_mes_plus.controller.ums;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.ums.UmsSupplier;
import com.rh.i_mes_plus.service.ums.IUmsSupplierService;
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
 * BOM料件供应商表
 *
 * @author hqb
 * @date 2020-09-19 14:42:47
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "BOM料件供应商表")
@RequestMapping("ums")
public class UmsSupplierController {
    @Autowired
    private IUmsSupplierService umsSupplierService;
    @Autowired
    private IUmsDepaService umsDepaService;

    @ApiOperation(value = "sap同步供应商数据到mes")
    @PostMapping("/umsSupplier/saveAll")
    public Result saveAll(@RequestBody List<Map<String,Object>> maps) {
        return umsSupplierService.saveAll(maps);
    }
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/umsSupplier/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        String code = MapUtil.getStr(params, "depaCode");
        List<String> umsDepas = StrUtil.isNotEmpty(code)?umsDepaService.getSon(params):new ArrayList<>();
        params.put("umsDepas",umsDepas);
        Page<UmsSupplier> list= umsSupplierService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/umsSupplier/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        UmsSupplier model = umsSupplierService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/umsSupplier/save")
    public Result save(@RequestBody UmsSupplier umsSupplier) {
        String depaName=umsDepaService.getDepaNameByCode(umsSupplier.getDepaCode());
        umsSupplier.setDepaName(depaName);
        umsSupplierService.saveOrUpdate(umsSupplier);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/umsSupplier/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        umsSupplierService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/umsSupplier/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<UmsSupplier> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, UmsSupplier.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        umsSupplierService.save(u);
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
    @PostMapping("/umsSupplier/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<UmsSupplier> umsSuppliers =new ArrayList<>();
        List<UmsSupplier> umsSupplierList = umsSupplierService.list(new QueryWrapper<UmsSupplier>().eq("cu_id", cuId));
        if (umsSupplierList.isEmpty()) {umsSuppliers.add(umsSupplierService.getById(0)); } else {
            for (UmsSupplier umsSupplier : umsSupplierList) {
                umsSuppliers.add(umsSupplier);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(umsSuppliers, "BOM料件供应商表导出", "BOM料件供应商表导出", UmsSupplier.class, "umsSupplier.xls", response);

    }
}
