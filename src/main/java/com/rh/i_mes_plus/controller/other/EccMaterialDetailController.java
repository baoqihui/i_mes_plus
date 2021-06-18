package com.rh.i_mes_plus.controller.other;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.other.EccMaterialDetail;
import com.rh.i_mes_plus.service.other.IEccMaterialDetailService;
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
 * ECC生产BOM
 *
 * @author hbq
 * @date 2021-01-09 15:34:09
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "ECC生产BOM")
@RequestMapping("ecc")
public class EccMaterialDetailController {
    @Autowired
    private IEccMaterialDetailService eccMaterialDetailService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/eccMaterialDetail/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= eccMaterialDetailService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/eccMaterialDetail/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        EccMaterialDetail model = eccMaterialDetailService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/eccMaterialDetail/save")
    public Result save(@RequestBody EccMaterialDetail eccMaterialDetail) {
        eccMaterialDetailService.saveOrUpdate(eccMaterialDetail);
        return Result.succeed("保存成功");
    }
    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/eccMaterialDetail/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<EccMaterialDetail>> map) {
        List<EccMaterialDetail> models = map.get("models");
        eccMaterialDetailService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/eccMaterialDetail/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        eccMaterialDetailService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/eccMaterialDetail/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<EccMaterialDetail> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, EccMaterialDetail.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        eccMaterialDetailService.save(u);
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
    @PostMapping("/eccMaterialDetail/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<EccMaterialDetail> eccMaterialDetails =new ArrayList<>();
        List<EccMaterialDetail> eccMaterialDetailList = eccMaterialDetailService.list(new QueryWrapper<EccMaterialDetail>().eq("cu_id", cuId));
        if (eccMaterialDetailList.isEmpty()) {eccMaterialDetails.add(eccMaterialDetailService.getById(0)); } else {
            for (EccMaterialDetail eccMaterialDetail : eccMaterialDetailList) {
                eccMaterialDetails.add(eccMaterialDetail);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(eccMaterialDetails, "ECC生产BOM导出", "ECC生产BOM导出", EccMaterialDetail.class, "eccMaterialDetail.xls", response);

    }
}
