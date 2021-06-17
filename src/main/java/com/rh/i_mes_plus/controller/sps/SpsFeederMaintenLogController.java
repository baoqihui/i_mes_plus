package com.rh.i_mes_plus.controller.sps;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sps.SpsFeederMaintenLog;
import com.rh.i_mes_plus.service.sps.ISpsFeederMaintenLogService;
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
 * 设备保养维修管理
 *
 * @author hbq
 * @date 2021-06-08 17:55:16
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "料枪保养维修管理")
@RequestMapping("sps")
public class SpsFeederMaintenLogController {
    @Autowired
    private ISpsFeederMaintenLogService spsFeederMaintenLogService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/spsFeederMaintenLog/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= spsFeederMaintenLogService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/spsFeederMaintenLog/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SpsFeederMaintenLog model = spsFeederMaintenLogService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/spsFeederMaintenLog/save")
    public Result save(@RequestBody SpsFeederMaintenLog spsFeederMaintenLog) {
        spsFeederMaintenLogService.saveOrUpdate(spsFeederMaintenLog);
        return Result.succeed("保存成功");
    }

    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/spsFeederMaintenLog/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<SpsFeederMaintenLog>> map) {
        List<SpsFeederMaintenLog> models = map.get("models");
        spsFeederMaintenLogService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/spsFeederMaintenLog/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        spsFeederMaintenLogService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/spsFeederMaintenLog/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SpsFeederMaintenLog> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SpsFeederMaintenLog.class);
            rowNum = list.size();
            if (rowNum > 0) {
                list.forEach(u -> {
                        spsFeederMaintenLogService.save(u);
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
    @PostMapping("/spsFeederMaintenLog/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<SpsFeederMaintenLog> spsFeederMaintenLogList = ids==null||ids.isEmpty()? spsFeederMaintenLogService.list():(List)spsFeederMaintenLogService.listByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(spsFeederMaintenLogList, "设备保养维修管理导出", "设备保养维修管理导出", SpsFeederMaintenLog.class, "spsFeederMaintenLog.xls", response);
    }
}
