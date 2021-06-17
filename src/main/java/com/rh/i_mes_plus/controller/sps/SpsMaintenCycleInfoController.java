package com.rh.i_mes_plus.controller.sps;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sps.SpsMaintenCycleInfo;
import com.rh.i_mes_plus.service.sps.ISpsMaintenCycleInfoService;
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
 * 保养周期信息表
 *
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "保养周期信息表")
@RequestMapping("sps")
public class SpsMaintenCycleInfoController {
    @Autowired
    private ISpsMaintenCycleInfoService spsMaintenCycleInfoService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/spsMaintenCycleInfo/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= spsMaintenCycleInfoService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/spsMaintenCycleInfo/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SpsMaintenCycleInfo model = spsMaintenCycleInfoService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/spsMaintenCycleInfo/save")
    public Result save(@RequestBody SpsMaintenCycleInfo spsMaintenCycleInfo) {
        spsMaintenCycleInfoService.saveOrUpdate(spsMaintenCycleInfo);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/spsMaintenCycleInfo/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<SpsMaintenCycleInfo>> map) {
        List<SpsMaintenCycleInfo> models = map.get("models");
        spsMaintenCycleInfoService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/spsMaintenCycleInfo/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        spsMaintenCycleInfoService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/spsMaintenCycleInfo/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SpsMaintenCycleInfo> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SpsMaintenCycleInfo.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        spsMaintenCycleInfoService.save(u);
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
    @PostMapping("/spsMaintenCycleInfo/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<SpsMaintenCycleInfo> spsMaintenCycleInfos =new ArrayList<>();
        List<SpsMaintenCycleInfo> spsMaintenCycleInfoList = spsMaintenCycleInfoService.list(new QueryWrapper<SpsMaintenCycleInfo>().eq("cu_id", cuId));
        if (spsMaintenCycleInfoList.isEmpty()) {spsMaintenCycleInfos.add(spsMaintenCycleInfoService.getById(0)); } else {
            for (SpsMaintenCycleInfo spsMaintenCycleInfo : spsMaintenCycleInfoList) {
                spsMaintenCycleInfos.add(spsMaintenCycleInfo);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(spsMaintenCycleInfos, "保养周期信息表导出", "保养周期信息表导出", SpsMaintenCycleInfo.class, "spsMaintenCycleInfo.xls", response);

    }
}
