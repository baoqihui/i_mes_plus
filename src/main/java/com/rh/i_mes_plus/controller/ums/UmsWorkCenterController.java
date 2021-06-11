package com.rh.i_mes_plus.controller.ums;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.ums.UmsWorkCenter;
import com.rh.i_mes_plus.service.ums.IUmsWorkCenterService;
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
 * 工作中心
 *
 * @author hbq
 * @date 2021-05-20 15:31:14
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "工作中心")
@RequestMapping("ums")
public class UmsWorkCenterController {
    @Autowired
    private IUmsWorkCenterService umsWorkCenterService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/umsWorkCenter/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= umsWorkCenterService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/umsWorkCenter/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        UmsWorkCenter model = umsWorkCenterService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/umsWorkCenter/save")
    public Result save(@RequestBody UmsWorkCenter umsWorkCenter) {
        umsWorkCenterService.saveOrUpdate(umsWorkCenter);
        return Result.succeed("保存成功");
    }

    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/umsWorkCenter/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<UmsWorkCenter>> map) {
        List<UmsWorkCenter> models = map.get("models");
        umsWorkCenterService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/umsWorkCenter/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        umsWorkCenterService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/umsWorkCenter/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<UmsWorkCenter> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, UmsWorkCenter.class);
            rowNum = list.size();
            if (rowNum > 0) {
                list.forEach(u -> {
                        umsWorkCenterService.save(u);
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
    @PostMapping("/umsWorkCenter/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<UmsWorkCenter> umsWorkCenterList = ids==null||ids.isEmpty()? umsWorkCenterService.list():(List)umsWorkCenterService.listByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(umsWorkCenterList, "工作中心导出", "工作中心导出", UmsWorkCenter.class, "umsWorkCenter.xls", response);
    }
}
