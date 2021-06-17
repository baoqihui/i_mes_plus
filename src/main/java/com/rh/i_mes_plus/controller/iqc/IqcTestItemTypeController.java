package com.rh.i_mes_plus.controller.iqc;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.iqc.IqcTestItemType;
import com.rh.i_mes_plus.service.iqc.IIqcTestItemTypeService;
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
 * 检测项目类型
 *
 * @author hqb
 * @date 2020-10-16 11:39:52
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "检测项目类型")
@RequestMapping("iqc")
public class IqcTestItemTypeController {
    @Autowired
    private IIqcTestItemTypeService iqcTestItemTypeService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/iqcTestItemType/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<IqcTestItemType> list= iqcTestItemTypeService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/iqcTestItemType/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        IqcTestItemType model = iqcTestItemTypeService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/iqcTestItemType/save")
    public Result save(@RequestBody IqcTestItemType iqcTestItemType) {
        iqcTestItemTypeService.saveOrUpdate(iqcTestItemType);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/iqcTestItemType/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        iqcTestItemTypeService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/iqcTestItemType/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<IqcTestItemType> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, IqcTestItemType.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        iqcTestItemTypeService.save(u);
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
    @PostMapping("/iqcTestItemType/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<IqcTestItemType> iqcTestItemTypes =new ArrayList<>();
        List<IqcTestItemType> iqcTestItemTypeList = iqcTestItemTypeService.list(new QueryWrapper<IqcTestItemType>().eq("cu_id", cuId));
        if (iqcTestItemTypeList.isEmpty()) {iqcTestItemTypes.add(iqcTestItemTypeService.getById(0)); } else {
            for (IqcTestItemType iqcTestItemType : iqcTestItemTypeList) {
                iqcTestItemTypes.add(iqcTestItemType);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(iqcTestItemTypes, "检测项目类型导出", "检测项目类型导出", IqcTestItemType.class, "iqcTestItemType.xls", response);

    }
}
