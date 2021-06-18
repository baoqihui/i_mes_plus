package com.rh.i_mes_plus.controller.pdt;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.pdt.PdtBomDetailReplaceItem;
import com.rh.i_mes_plus.service.pdt.IPdtBomDetailReplaceItemService;
import com.rh.i_mes_plus.util.EasyPoiExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * bom详情替代料信息
 *
 * @author hbq
 * @date 2021-06-09 20:13:20
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "bom详情替代料信息")
@RequestMapping("pdt")
public class PdtBomDetailReplaceItemController {
    @Autowired
    private IPdtBomDetailReplaceItemService pdtBomDetailReplaceItemService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/pdtBomDetailReplaceItem/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= pdtBomDetailReplaceItemService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/pdtBomDetailReplaceItem/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        PdtBomDetailReplaceItem model = pdtBomDetailReplaceItemService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/pdtBomDetailReplaceItem/save")
    public Result save(@RequestBody PdtBomDetailReplaceItem pdtBomDetailReplaceItem) {
        pdtBomDetailReplaceItemService.saveOrUpdate(pdtBomDetailReplaceItem);
        return Result.succeed("保存成功");
    }

    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/pdtBomDetailReplaceItem/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<PdtBomDetailReplaceItem>> map) {
        List<PdtBomDetailReplaceItem> models = map.get("models");
        pdtBomDetailReplaceItemService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/pdtBomDetailReplaceItem/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        pdtBomDetailReplaceItemService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/pdtBomDetailReplaceItem/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<PdtBomDetailReplaceItem> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, PdtBomDetailReplaceItem.class);
            rowNum = list.size();
            if (rowNum > 0) {
                list.forEach(u -> {
                        pdtBomDetailReplaceItemService.save(u);
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
    @PostMapping("/pdtBomDetailReplaceItem/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<PdtBomDetailReplaceItem> pdtBomDetailReplaceItemList = ids==null||ids.isEmpty()? pdtBomDetailReplaceItemService.list():(List)pdtBomDetailReplaceItemService.listByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(pdtBomDetailReplaceItemList, "bom详情替代料信息导出", "bom详情替代料信息导出", PdtBomDetailReplaceItem.class, "pdtBomDetailReplaceItem.xls", response);
    }
}
