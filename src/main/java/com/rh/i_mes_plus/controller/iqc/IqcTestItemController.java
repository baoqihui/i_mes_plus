package com.rh.i_mes_plus.controller.iqc;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.iqc.IqcTestItem;
import com.rh.i_mes_plus.service.iqc.IIqcTestItemService;
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
 * 检测项目
 *
 * @author hqb
 * @date 2020-10-16 11:39:51
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "检测项目")
@RequestMapping("iqc")
public class IqcTestItemController {
    @Autowired
    private IIqcTestItemService iqcTestItemService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/iqcTestItem/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= iqcTestItemService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/iqcTestItem/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        IqcTestItem model = iqcTestItemService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/iqcTestItem/save")
    public Result save(@RequestBody IqcTestItem iqcTestItem) {
        iqcTestItemService.saveOrUpdate(iqcTestItem);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/iqcTestItem/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        iqcTestItemService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/iqcTestItem/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<IqcTestItem> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, IqcTestItem.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        iqcTestItemService.save(u);
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
    @PostMapping("/iqcTestItem/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<IqcTestItem> iqcTestItems =new ArrayList<>();
        List<IqcTestItem> iqcTestItemList = iqcTestItemService.list(new QueryWrapper<IqcTestItem>().eq("cu_id", cuId));
        if (iqcTestItemList.isEmpty()) {iqcTestItems.add(iqcTestItemService.getById(0)); } else {
            for (IqcTestItem iqcTestItem : iqcTestItemList) {
                iqcTestItems.add(iqcTestItem);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(iqcTestItems, "检测项目导出", "检测项目导出", IqcTestItem.class, "iqcTestItem.xls", response);

    }
}
