package com.rh.i_mes_plus.controller.sps;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sps.SpsMaintenLogInfo;
import com.rh.i_mes_plus.service.sps.ISpsMaintenLogInfoService;
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
 * 保养记录信息表
 *
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "保养记录信息表")
@RequestMapping("sps")
public class SpsMaintenLogInfoController {
    @Autowired
    private ISpsMaintenLogInfoService spsMaintenLogInfoService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/spsMaintenLogInfo/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= spsMaintenLogInfoService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/spsMaintenLogInfo/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SpsMaintenLogInfo model = spsMaintenLogInfoService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/spsMaintenLogInfo/save")
    public Result save(@RequestBody SpsMaintenLogInfo spsMaintenLogInfo) {
        return spsMaintenLogInfoService.saveAll(spsMaintenLogInfo);
    }
    /**
     * 批量保养
     */
    @ApiOperation(value = "批量保养")
    @PostMapping("/spsMaintenLogInfo/allMaintain")
    public Result allMaintain(@RequestBody List<SpsMaintenLogInfo> spsMaintenLogInfos) {
        return spsMaintenLogInfoService.allMaintain(spsMaintenLogInfos);
    }
    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/spsMaintenLogInfo/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<SpsMaintenLogInfo>> map) {
        List<SpsMaintenLogInfo> models = map.get("models");
        spsMaintenLogInfoService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/spsMaintenLogInfo/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        spsMaintenLogInfoService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/spsMaintenLogInfo/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SpsMaintenLogInfo> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SpsMaintenLogInfo.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        spsMaintenLogInfoService.save(u);
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
    @PostMapping("/spsMaintenLogInfo/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<SpsMaintenLogInfo> spsMaintenLogInfos =new ArrayList<>();
        List<SpsMaintenLogInfo> spsMaintenLogInfoList = spsMaintenLogInfoService.list(new QueryWrapper<SpsMaintenLogInfo>().eq("cu_id", cuId));
        if (spsMaintenLogInfoList.isEmpty()) {spsMaintenLogInfos.add(spsMaintenLogInfoService.getById(0)); } else {
            for (SpsMaintenLogInfo spsMaintenLogInfo : spsMaintenLogInfoList) {
                spsMaintenLogInfos.add(spsMaintenLogInfo);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(spsMaintenLogInfos, "保养记录信息表导出", "保养记录信息表导出", SpsMaintenLogInfo.class, "spsMaintenLogInfo.xls", response);

    }
}
