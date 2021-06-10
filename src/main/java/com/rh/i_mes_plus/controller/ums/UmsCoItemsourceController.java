package com.rh.i_mes_plus.controller.ums;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.ums.UmsCoItemsource;
import com.rh.i_mes_plus.service.ums.IUmsCoItemsourceService;
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
 * 物料类型
 *
 * @author hqb
 * @date 2020-09-21 16:35:09
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "物料类型")
@RequestMapping("ums")
public class UmsCoItemsourceController {
    @Autowired
    private IUmsCoItemsourceService umsCoItemsourceService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/umsCoItemsource/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<UmsCoItemsource> list= umsCoItemsourceService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/umsCoItemsource/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        UmsCoItemsource model = umsCoItemsourceService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/umsCoItemsource/save")
    public Result save(@RequestBody UmsCoItemsource umsCoItemsource) {
        UmsCoItemsource existUmsCoItemsource = umsCoItemsourceService.getOne(new QueryWrapper<UmsCoItemsource>()
                .eq("ITEM_SOURCE_CODE", umsCoItemsource.getItemSourceCode())
        );
        if(existUmsCoItemsource==null){
            umsCoItemsourceService.save(umsCoItemsource);
            return Result.succeed("保存成功");
        }else {
            umsCoItemsourceService.update(umsCoItemsource,new UpdateWrapper<UmsCoItemsource>()
                    .eq("ITEM_SOURCE_CODE", umsCoItemsource.getItemSourceCode())
            );
            return Result.succeed("修改成功");
        }
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/umsCoItemsource/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        umsCoItemsourceService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/umsCoItemsource/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<UmsCoItemsource> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, UmsCoItemsource.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        umsCoItemsourceService.save(u);
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
    @PostMapping("/umsCoItemsource/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<UmsCoItemsource> umsCoItemsources =new ArrayList<>();
        List<UmsCoItemsource> umsCoItemsourceList = umsCoItemsourceService.list(new QueryWrapper<UmsCoItemsource>().eq("cu_id", cuId));
        if (umsCoItemsourceList.isEmpty()) {umsCoItemsources.add(umsCoItemsourceService.getById(0)); } else {
            for (UmsCoItemsource umsCoItemsource : umsCoItemsourceList) {
                umsCoItemsources.add(umsCoItemsource);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(umsCoItemsources, "物料类型导出", "物料类型导出", UmsCoItemsource.class, "umsCoItemsource.xls", response);

    }
}
