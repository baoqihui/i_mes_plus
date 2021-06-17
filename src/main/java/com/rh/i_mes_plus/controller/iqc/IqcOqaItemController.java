package com.rh.i_mes_plus.controller.iqc;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.iqc.IqcOqaItem;
import com.rh.i_mes_plus.service.iqc.IIqcOqaItemService;
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
 * 抽样项目
 *
 * @author hbq
 * @date 2020-10-22 16:28:19
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "抽样项目")
@RequestMapping("iqc")
public class IqcOqaItemController {
    @Autowired
    private IIqcOqaItemService iqcOqaItemService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/iqcOqaItem/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= iqcOqaItemService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/iqcOqaItem/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        IqcOqaItem model = iqcOqaItemService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/iqcOqaItem/save")
    public Result save(@RequestBody IqcOqaItem iqcOqaItem) {
        iqcOqaItemService.saveOrUpdate(iqcOqaItem);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/iqcOqaItem/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<IqcOqaItem>> map) {
        List<IqcOqaItem> models = map.get("models");
        iqcOqaItemService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/iqcOqaItem/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        iqcOqaItemService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/iqcOqaItem/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<IqcOqaItem> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, IqcOqaItem.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        iqcOqaItemService.save(u);
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
    @PostMapping("/iqcOqaItem/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<IqcOqaItem> iqcOqaItems =new ArrayList<>();
        List<IqcOqaItem> iqcOqaItemList = iqcOqaItemService.list(new QueryWrapper<IqcOqaItem>().eq("cu_id", cuId));
        if (iqcOqaItemList.isEmpty()) {iqcOqaItems.add(iqcOqaItemService.getById(0)); } else {
            for (IqcOqaItem iqcOqaItem : iqcOqaItemList) {
                iqcOqaItems.add(iqcOqaItem);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(iqcOqaItems, "抽样项目导出", "抽样项目导出", IqcOqaItem.class, "iqcOqaItem.xls", response);

    }
}
