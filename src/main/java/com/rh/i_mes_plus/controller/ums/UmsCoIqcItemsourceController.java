package com.rh.i_mes_plus.controller.ums;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.ums.UmsCoIqcItemsource;
import com.rh.i_mes_plus.service.ums.IUmsCoIqcItemsourceService;
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
 * IQC检验分类表
 *
 * @author hqb
 * @date 2020-09-21 16:35:09
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "IQC检验分类表")
@RequestMapping("ums")
public class UmsCoIqcItemsourceController {
    @Autowired
    private IUmsCoIqcItemsourceService umsCoIqcItemsourceService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/umsCoIqcItemsource/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<UmsCoIqcItemsource> list= umsCoIqcItemsourceService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/umsCoIqcItemsource/sel/{id}")
    public Result findUserById(@PathVariable Long itemSourcecode) {
        UmsCoIqcItemsource model = umsCoIqcItemsourceService.getById(itemSourcecode);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/umsCoIqcItemsource/save")
    public Result save(@RequestBody UmsCoIqcItemsource umsCoIqcItemsource) {
        umsCoIqcItemsourceService.saveOrUpdate(umsCoIqcItemsource);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/umsCoIqcItemsource/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        umsCoIqcItemsourceService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/umsCoIqcItemsource/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<UmsCoIqcItemsource> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, UmsCoIqcItemsource.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        umsCoIqcItemsourceService.save(u);
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
    @PostMapping("/umsCoIqcItemsource/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<UmsCoIqcItemsource> umsCoIqcItemsources =new ArrayList<>();
        List<UmsCoIqcItemsource> umsCoIqcItemsourceList = umsCoIqcItemsourceService.list(new QueryWrapper<UmsCoIqcItemsource>().eq("cu_id", cuId));
        if (umsCoIqcItemsourceList.isEmpty()) {umsCoIqcItemsources.add(umsCoIqcItemsourceService.getById(0)); } else {
            for (UmsCoIqcItemsource umsCoIqcItemsource : umsCoIqcItemsourceList) {
                umsCoIqcItemsources.add(umsCoIqcItemsource);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(umsCoIqcItemsources, "IQC检验分类表导出", "IQC检验分类表导出", UmsCoIqcItemsource.class, "umsCoIqcItemsource.xls", response);

    }
}
