package com.rh.i_mes_plus.controller.ums;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.ums.UmsItemMes;
import com.rh.i_mes_plus.service.ums.IUmsItemMesService;
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
 * 物料信息（mes获取）
 *
 * @author hqb
 * @date 2020-09-21 11:13:27
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "物料信息（mes获取）")
@RequestMapping("ums")
public class UmsItemMesController {
    @Autowired
    private IUmsItemMesService umsItemMesService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/umsItemMes/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<UmsItemMes> list= umsItemMesService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/umsItemMes/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        UmsItemMes model = umsItemMesService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增")
    @PostMapping("/umsItemMes/save")
    public Result save(@RequestBody UmsItemMes umsItemMes) {
        return umsItemMesService.saveAll(umsItemMes);
    }
    @ApiOperation(value = "更新")
    @PostMapping("/umsItemMes/update")
    public Result update(@RequestBody UmsItemMes umsItemMes) {
        umsItemMesService.updateById(umsItemMes);
        return Result.succeed("更新成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/umsItemMes/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        umsItemMesService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/umsItemMes/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<UmsItemMes> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, UmsItemMes.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        umsItemMesService.save(u);
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
    @PostMapping("/umsItemMes/leadOut")
    public void leadOut( HttpServletResponse response) throws IOException {
        List<UmsItemMes> umsItemMess =new ArrayList<>();
        List<UmsItemMes> umsItemMesList = umsItemMesService.list(new QueryWrapper<UmsItemMes>());
        if (umsItemMesList.isEmpty()) {umsItemMess.add(umsItemMesService.getById(0)); } else {
            for (UmsItemMes umsItemMes : umsItemMesList) {
                umsItemMess.add(umsItemMes);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(umsItemMess, "物料信息（mes获取）导出", "物料信息（mes获取）导出", UmsItemMes.class, "umsItemMes.xls", response);

    }
}
