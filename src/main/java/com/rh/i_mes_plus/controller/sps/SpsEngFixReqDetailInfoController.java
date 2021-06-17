package com.rh.i_mes_plus.controller.sps;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sps.SpsEngFixReqDetailInfo;
import com.rh.i_mes_plus.service.sps.ISpsEngFixReqDetailInfoService;
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
 * 工程治具借出详情表
 *
 * @author hbq
 * @date 2021-02-20 09:14:10
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "工程治具借出详情表")
@RequestMapping("sps")
public class SpsEngFixReqDetailInfoController {
    @Autowired
    private ISpsEngFixReqDetailInfoService spsEngFixReqDetailInfoService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/spsEngFixReqDetailInfo/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= spsEngFixReqDetailInfoService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/spsEngFixReqDetailInfo/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SpsEngFixReqDetailInfo model = spsEngFixReqDetailInfoService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/spsEngFixReqDetailInfo/save")
    public Result save(@RequestBody SpsEngFixReqDetailInfo spsEngFixReqDetailInfo) {
        spsEngFixReqDetailInfoService.saveOrUpdate(spsEngFixReqDetailInfo);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/spsEngFixReqDetailInfo/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<SpsEngFixReqDetailInfo>> map) {
        List<SpsEngFixReqDetailInfo> models = map.get("models");
        spsEngFixReqDetailInfoService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/spsEngFixReqDetailInfo/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        spsEngFixReqDetailInfoService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/spsEngFixReqDetailInfo/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SpsEngFixReqDetailInfo> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SpsEngFixReqDetailInfo.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        spsEngFixReqDetailInfoService.save(u);
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
    @PostMapping("/spsEngFixReqDetailInfo/leadOut")
    public void leadOut(HttpServletResponse response) throws IOException {
        List<SpsEngFixReqDetailInfo> spsEngFixReqDetailInfos =new ArrayList<>();
        List<SpsEngFixReqDetailInfo> spsEngFixReqDetailInfoList = spsEngFixReqDetailInfoService.list(new QueryWrapper<SpsEngFixReqDetailInfo>());
        if (spsEngFixReqDetailInfoList.isEmpty()) {spsEngFixReqDetailInfos.add(spsEngFixReqDetailInfoService.getById(0)); } else {
            for (SpsEngFixReqDetailInfo spsEngFixReqDetailInfo : spsEngFixReqDetailInfoList) {
                spsEngFixReqDetailInfos.add(spsEngFixReqDetailInfo);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(spsEngFixReqDetailInfos, "工程治具借出详情表导出", "工程治具借出详情表导出", SpsEngFixReqDetailInfo.class, "spsEngFixReqDetailInfo.xls", response);

    }
}
