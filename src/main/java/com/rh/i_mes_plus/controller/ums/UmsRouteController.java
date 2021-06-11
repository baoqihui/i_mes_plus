package com.rh.i_mes_plus.controller.ums;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.ums.UmsRoute;
import com.rh.i_mes_plus.service.ums.IUmsRouteService;
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
 * 流程控制
 *
 * @author hbq
 * @date 2021-06-01 09:33:59
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "流程控制")
@RequestMapping("ums")
public class UmsRouteController {
    @Autowired
    private IUmsRouteService umsRouteService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/umsRoute/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= umsRouteService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/umsRoute/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        UmsRoute model = umsRouteService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/umsRoute/save")
    public Result save(@RequestBody UmsRoute umsRoute) {
        umsRouteService.saveOrUpdate(umsRoute);
        return Result.succeed("保存成功");
    }

    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/umsRoute/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<UmsRoute>> map) {
        List<UmsRoute> models = map.get("models");
        umsRouteService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/umsRoute/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        umsRouteService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/umsRoute/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<UmsRoute> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, UmsRoute.class);
            rowNum = list.size();
            if (rowNum > 0) {
                list.forEach(u -> {
                        umsRouteService.save(u);
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
    @PostMapping("/umsRoute/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<UmsRoute> umsRouteList = ids==null||ids.isEmpty()? umsRouteService.list():(List)umsRouteService.listByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(umsRouteList, "流程控制导出", "流程控制导出", UmsRoute.class, "umsRoute.xls", response);
    }
}
