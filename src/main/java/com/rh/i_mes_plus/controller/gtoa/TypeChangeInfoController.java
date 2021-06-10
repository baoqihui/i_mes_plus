package com.rh.i_mes_plus.controller.gtoa;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.gtoa.TypeChangeInfo;
import com.rh.i_mes_plus.service.gtoa.ITypeChangeInfoService;
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
 * 变更类型表
 *
 * @author hbq
 * @date 2020-10-22 19:36:56
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "变更类型表")
@RequestMapping("typ")
public class TypeChangeInfoController {
    @Autowired
    private ITypeChangeInfoService typeChangeInfoService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/typeChangeInfo/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= typeChangeInfoService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/typeChangeInfo/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        TypeChangeInfo model = typeChangeInfoService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/typeChangeInfo/save")
    public Result save(@RequestBody TypeChangeInfo typeChangeInfo) {
        typeChangeInfoService.saveOrUpdate(typeChangeInfo);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/typeChangeInfo/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<TypeChangeInfo>> map) {
        List<TypeChangeInfo> models = map.get("models");
        typeChangeInfoService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/typeChangeInfo/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        typeChangeInfoService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/typeChangeInfo/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<TypeChangeInfo> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, TypeChangeInfo.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        typeChangeInfoService.save(u);
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
    @PostMapping("/typeChangeInfo/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<TypeChangeInfo> typeChangeInfos =new ArrayList<>();
        List<TypeChangeInfo> typeChangeInfoList = typeChangeInfoService.list(new QueryWrapper<TypeChangeInfo>().eq("cu_id", cuId));
        if (typeChangeInfoList.isEmpty()) {typeChangeInfos.add(typeChangeInfoService.getById(0)); } else {
            for (TypeChangeInfo typeChangeInfo : typeChangeInfoList) {
                typeChangeInfos.add(typeChangeInfo);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(typeChangeInfos, "变更类型表导出", "变更类型表导出", TypeChangeInfo.class, "typeChangeInfo.xls", response);

    }
}
