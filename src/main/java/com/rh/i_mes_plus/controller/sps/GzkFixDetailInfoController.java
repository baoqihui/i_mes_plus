package com.rh.i_mes_plus.controller.sps;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sps.GzkFixDetailInfo;
import com.rh.i_mes_plus.service.sps.IGzkFixDetailInfoService;
import com.rh.i_mes_plus.util.EasyPoiExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工装治具
 *
 * @author hbq
 * @date 2021-02-23 10:06:16
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "工装治具详情")
@RequestMapping("gzk")
public class GzkFixDetailInfoController {
    @Autowired
    private IGzkFixDetailInfoService gzkFixDetailInfoService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/gzkFixDetailInfo/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= gzkFixDetailInfoService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/gzkFixDetailInfo/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        GzkFixDetailInfo model = gzkFixDetailInfoService.getById(id);
        return Result.succeed(model, "查询成功");
    }
    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/gzkFixDetailInfo/save")
    public Result save(@RequestBody GzkFixDetailInfo gzkFixDetailInfo) {
        Long id = gzkFixDetailInfo.getId();
        if (id == null) {
            GzkFixDetailInfo one = gzkFixDetailInfoService.getOne(new LambdaQueryWrapper<GzkFixDetailInfo>()
                    .eq(GzkFixDetailInfo::getFixNo, gzkFixDetailInfo.getFixNo())
            );
            if (one != null) {
                return Result.failed("编号重复");
            }
            gzkFixDetailInfoService.save(gzkFixDetailInfo);
        }
        gzkFixDetailInfoService.updateById(gzkFixDetailInfo);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/gzkFixDetailInfo/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<GzkFixDetailInfo>> map) {
        List<GzkFixDetailInfo> models = map.get("models");
        gzkFixDetailInfoService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/gzkFixDetailInfo/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        gzkFixDetailInfoService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/gzkFixDetailInfo/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<GzkFixDetailInfo> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, GzkFixDetailInfo.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        gzkFixDetailInfoService.save(u);
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
    @PostMapping("/gzkFixDetailInfo/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        Map<String, Object> result=new HashMap<>();
        List<Map> list = gzkFixDetailInfoService.leadOut(ids);
        TemplateExportParams params = new TemplateExportParams("src/main/resources/ExcelModel/工装治具详情模板.xls");
        result.put("list",list);
        //导出操作
        EasyPoiExcelUtil.downLoadExcel("工装治具详情表.xls",response,ExcelExportUtil.exportExcel(params, result));
    }
}
