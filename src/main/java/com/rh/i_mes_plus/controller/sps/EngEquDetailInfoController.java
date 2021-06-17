package com.rh.i_mes_plus.controller.sps;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sps.EngEquDetailInfo;
import com.rh.i_mes_plus.service.sps.IEngEquDetailInfoService;
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
 * 工程设备详情信息表
 *
 * @author hbq
 * @date 2021-02-24 18:51:21
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "工程设备详情信息表")
@RequestMapping("eng")
public class EngEquDetailInfoController {
    @Autowired
    private IEngEquDetailInfoService engEquDetailInfoService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/engEquDetailInfo/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= engEquDetailInfoService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/engEquDetailInfo/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        EngEquDetailInfo model = engEquDetailInfoService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/engEquDetailInfo/save")
    public Result save(@RequestBody EngEquDetailInfo engEquDetailInfo) {
        engEquDetailInfoService.saveOrUpdate(engEquDetailInfo);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/engEquDetailInfo/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<EngEquDetailInfo>> map) {
        List<EngEquDetailInfo> models = map.get("models");
        engEquDetailInfoService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/engEquDetailInfo/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        engEquDetailInfoService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/engEquDetailInfo/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<EngEquDetailInfo> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, EngEquDetailInfo.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        engEquDetailInfoService.save(u);
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
    @PostMapping("/engEquDetailInfo/leadOut")
    public void leadOut(HttpServletResponse response) throws IOException {
        List<EngEquDetailInfo> engEquDetailInfos =new ArrayList<>();
        List<EngEquDetailInfo> engEquDetailInfoList = engEquDetailInfoService.list(new QueryWrapper<EngEquDetailInfo>());
        if (engEquDetailInfoList.isEmpty()) {engEquDetailInfos.add(engEquDetailInfoService.getById(0)); } else {
            for (EngEquDetailInfo engEquDetailInfo : engEquDetailInfoList) {
                engEquDetailInfos.add(engEquDetailInfo);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(engEquDetailInfos, "工程设备详情信息表导出", "工程设备详情信息表导出", EngEquDetailInfo.class, "engEquDetailInfo.xls", response);

    }
}
