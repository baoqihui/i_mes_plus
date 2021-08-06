package com.rh.i_mes_plus.controller.sps;
import java.io.IOException;
import java.util.List;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.rh.i_mes_plus.model.sps.GzkPartsItem;
import com.rh.i_mes_plus.service.sps.IGzkPartsItemService;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;


import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import com.rh.i_mes_plus.util.EasyPoiExcelUtil;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;

/**
 * 工装备品物料
 *
 * @author hbq
 * @date 2021-06-23 15:54:13
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "工装备品物料")
@RequestMapping("gzk")
public class GzkPartsItemController {
    @Autowired
    private IGzkPartsItemService gzkPartsItemService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/gzkPartsItem/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= gzkPartsItemService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    @ApiOperation(value = "查询全部列表")
    @PostMapping("/gzkPartsItem/listAll")
    public Result listAll(@RequestBody Map<String, Object> params) {
        List<GzkPartsItem> list = gzkPartsItemService.list();
        return Result.succeed(list,"查询成功");
    }
    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/gzkPartsItem/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        GzkPartsItem model = gzkPartsItemService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/gzkPartsItem/save")
    public Result save(@RequestBody GzkPartsItem gzkPartsItem) {
        gzkPartsItemService.saveOrUpdate(gzkPartsItem);
        return Result.succeed("保存成功");
    }

    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/gzkPartsItem/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<GzkPartsItem>> map) {
        List<GzkPartsItem> models = map.get("models");
        gzkPartsItemService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/gzkPartsItem/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        gzkPartsItemService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/gzkPartsItem/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<GzkPartsItem> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, GzkPartsItem.class);
            rowNum = list.size();
            if (rowNum > 0) {
                list.forEach(u -> {
                        gzkPartsItemService.save(u);
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
    @PostMapping("/gzkPartsItem/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<GzkPartsItem> gzkPartsItemList = ids==null||ids.isEmpty()? gzkPartsItemService.list():(List)gzkPartsItemService.listByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(gzkPartsItemList, "工装备品物料导出", "工装备品物料导出", GzkPartsItem.class, "gzkPartsItem.xls", response);
    }
}
