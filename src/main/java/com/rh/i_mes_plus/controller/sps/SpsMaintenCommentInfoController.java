package com.rh.i_mes_plus.controller.sps;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sps.SpsMaintenCommentInfo;
import com.rh.i_mes_plus.service.sps.ISpsMaintenCommentInfoService;
import com.rh.i_mes_plus.service.ums.IUmsDepaService;
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
 * 保养内容信息表
 *
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "保养内容信息表")
@RequestMapping("sps")
public class SpsMaintenCommentInfoController {
    @Autowired
    private ISpsMaintenCommentInfoService spsMaintenCommentInfoService;
    @Autowired
    private IUmsDepaService umsDepaService;
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/spsMaintenCommentInfo/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        String code = MapUtil.getStr(params, "depaCode");
        List<String> umsDepas = StrUtil.isNotEmpty(code)?umsDepaService.getSon(params):new ArrayList<>();
        params.put("umsDepas",umsDepas);
        Page<Map> list= spsMaintenCommentInfoService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/spsMaintenCommentInfo/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SpsMaintenCommentInfo model = spsMaintenCommentInfoService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/spsMaintenCommentInfo/save")
    public Result save(@RequestBody SpsMaintenCommentInfo spsMaintenCommentInfo) {
        return spsMaintenCommentInfoService.saveAll(spsMaintenCommentInfo);
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/spsMaintenCommentInfo/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<SpsMaintenCommentInfo>> map) {
        List<SpsMaintenCommentInfo> models = map.get("models");
        spsMaintenCommentInfoService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/spsMaintenCommentInfo/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        spsMaintenCommentInfoService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/spsMaintenCommentInfo/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SpsMaintenCommentInfo> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SpsMaintenCommentInfo.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        spsMaintenCommentInfoService.save(u);
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
    @PostMapping("/spsMaintenCommentInfo/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<SpsMaintenCommentInfo> spsMaintenCommentInfos =new ArrayList<>();
        List<SpsMaintenCommentInfo> spsMaintenCommentInfoList = spsMaintenCommentInfoService.list(new QueryWrapper<SpsMaintenCommentInfo>().eq("cu_id", cuId));
        if (spsMaintenCommentInfoList.isEmpty()) {spsMaintenCommentInfos.add(spsMaintenCommentInfoService.getById(0)); } else {
            for (SpsMaintenCommentInfo spsMaintenCommentInfo : spsMaintenCommentInfoList) {
                spsMaintenCommentInfos.add(spsMaintenCommentInfo);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(spsMaintenCommentInfos, "保养内容信息表导出", "保养内容信息表导出", SpsMaintenCommentInfo.class, "spsMaintenCommentInfo.xls", response);

    }
}
