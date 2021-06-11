package com.rh.i_mes_plus.controller.ums;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.ums.UmsTechnicsPath;
import com.rh.i_mes_plus.service.ums.IUmsTechnicsPathService;
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
 * 工艺路线
 *
 * @author hbq
 * @date 2021-06-01 10:57:04
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "工艺路线")
@RequestMapping("ums")
public class UmsTechnicsPathController {
    @Autowired
    private IUmsTechnicsPathService umsTechnicsPathService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/umsTechnicsPath/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= umsTechnicsPathService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/umsTechnicsPath/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        UmsTechnicsPath model = umsTechnicsPathService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/umsTechnicsPath/save")
    public Result save(@RequestBody UmsTechnicsPath umsTechnicsPath) {
        umsTechnicsPathService.saveOrUpdate(umsTechnicsPath);
        return Result.succeed("保存成功");
    }

    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/umsTechnicsPath/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<UmsTechnicsPath>> map) {
        List<UmsTechnicsPath> models = map.get("models");
        umsTechnicsPathService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/umsTechnicsPath/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        umsTechnicsPathService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/umsTechnicsPath/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<UmsTechnicsPath> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, UmsTechnicsPath.class);
            rowNum = list.size();
            if (rowNum > 0) {
                list.forEach(u -> {
                        umsTechnicsPathService.save(u);
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
    @PostMapping("/umsTechnicsPath/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<UmsTechnicsPath> umsTechnicsPathList = ids==null||ids.isEmpty()? umsTechnicsPathService.list():(List)umsTechnicsPathService.listByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(umsTechnicsPathList, "工艺路线导出", "工艺路线导出", UmsTechnicsPath.class, "umsTechnicsPath.xls", response);
    }
}
