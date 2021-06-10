package com.rh.i_mes_plus.controller.ums;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.ums.UmsCoBarcodeRoul;
import com.rh.i_mes_plus.service.ums.IUmsCoBarcodeRoulService;
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
 * 物料条码规则
 *
 * @author hqb
 * @date 2020-09-21 16:35:09
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "物料条码规则")
@RequestMapping("ums")
public class UmsCoBarcodeRoulController {
    @Autowired
    private IUmsCoBarcodeRoulService umsCoBarcodeRoulService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/umsCoBarcodeRoul/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<UmsCoBarcodeRoul> list= umsCoBarcodeRoulService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/umsCoBarcodeRoul/sel/{id}")
    public Result findUserById(@PathVariable Long tbrId) {
        UmsCoBarcodeRoul model = umsCoBarcodeRoulService.getById(tbrId);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/umsCoBarcodeRoul/save")
    public Result save(@RequestBody UmsCoBarcodeRoul umsCoBarcodeRoul) {
        umsCoBarcodeRoulService.saveOrUpdate(umsCoBarcodeRoul);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/umsCoBarcodeRoul/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        umsCoBarcodeRoulService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/umsCoBarcodeRoul/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<UmsCoBarcodeRoul> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, UmsCoBarcodeRoul.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        umsCoBarcodeRoulService.save(u);
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
    @PostMapping("/umsCoBarcodeRoul/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<UmsCoBarcodeRoul> umsCoBarcodeRouls =new ArrayList<>();
        List<UmsCoBarcodeRoul> umsCoBarcodeRoulList = umsCoBarcodeRoulService.list(new QueryWrapper<UmsCoBarcodeRoul>().eq("cu_id", cuId));
        if (umsCoBarcodeRoulList.isEmpty()) {umsCoBarcodeRouls.add(umsCoBarcodeRoulService.getById(0)); } else {
            for (UmsCoBarcodeRoul umsCoBarcodeRoul : umsCoBarcodeRoulList) {
                umsCoBarcodeRouls.add(umsCoBarcodeRoul);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(umsCoBarcodeRouls, "物料条码规则导出", "物料条码规则导出", UmsCoBarcodeRoul.class, "umsCoBarcodeRoul.xls", response);

    }
}
