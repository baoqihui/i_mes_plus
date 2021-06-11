package com.rh.i_mes_plus.controller.ums;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.ums.UmsTechnics;
import com.rh.i_mes_plus.service.ums.IUmsTechnicsService;
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
 * 工艺
 *
 * @author hbq
 * @date 2021-06-01 10:57:04
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "工艺")
@RequestMapping("ums")
public class UmsTechnicsController {
    @Autowired
    private IUmsTechnicsService umsTechnicsService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/umsTechnics/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= umsTechnicsService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/umsTechnics/sel/{id}")
    public Result isPcbUnpack(@PathVariable Long id) {
        Map<String, Object> map=umsTechnicsService.isPcbUnpack(id);
        return Result.succeed(map, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/umsTechnics/save")
    public Result save(@RequestBody UmsTechnics umsTechnics) {
        Integer isDef = umsTechnics.getIsDef();
        if (isDef==1){
            umsTechnicsService.update(new LambdaUpdateWrapper<UmsTechnics>()
                    .eq(UmsTechnics::getModelCode,umsTechnics.getModelCode())
                    .eq(UmsTechnics::getMsCode,umsTechnics.getMsCode())
                    .eq(UmsTechnics::getScFlag,umsTechnics.getScFlag())
                    .eq(UmsTechnics::getIsValue,1)
                    .eq(UmsTechnics::getIsDef,1)
                    .set(UmsTechnics::getIsDef,0)
            );
        }
        umsTechnicsService.saveOrUpdate(umsTechnics);
        return Result.succeed("保存成功");
    }

    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/umsTechnics/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<UmsTechnics>> map) {
        List<UmsTechnics> models = map.get("models");
        umsTechnicsService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/umsTechnics/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        umsTechnicsService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/umsTechnics/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<UmsTechnics> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, UmsTechnics.class);
            rowNum = list.size();
            if (rowNum > 0) {
                list.forEach(u -> {
                        umsTechnicsService.save(u);
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
    @PostMapping("/umsTechnics/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<UmsTechnics> umsTechnicsList = ids==null||ids.isEmpty()? umsTechnicsService.list():(List)umsTechnicsService.listByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(umsTechnicsList, "工艺导出", "工艺导出", UmsTechnics.class, "umsTechnics.xls", response);
    }
}
