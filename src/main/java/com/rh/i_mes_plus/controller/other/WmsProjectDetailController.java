package com.rh.i_mes_plus.controller.other;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.ProjectAndFaceDTO;
import com.rh.i_mes_plus.dto.WmsProjectDTO;
import com.rh.i_mes_plus.model.other.WmsProjectDetail;
import com.rh.i_mes_plus.service.other.IWmsProjectDetailService;
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
 * 工单明细表
 *
 * @author hbq
 * @date 2020-10-30 11:19:39
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "工单明细表")
@RequestMapping("wms")
public class WmsProjectDetailController {
    @Autowired
    private IWmsProjectDetailService wmsProjectDetailService;

    @ApiOperation(value = "sap同步project信息到mes")
    @PostMapping("/wmsProjectDetail/saveAll")
    public Result saveAll(@RequestBody List<WmsProjectDTO> wmsProjectDTOS) {
        return wmsProjectDetailService.saveAll(wmsProjectDTOS);
    }
    /**
     * 通过工单号集合查询工单明细
     */
    @ApiOperation(value = "通过工单号集合查询工单明细")
    @PostMapping("/wmsProjectDetail/getDetailByProjectIds")
    public Result getDetailByProjectIds(@RequestBody Map<String, List<ProjectAndFaceDTO>> params) {
        return wmsProjectDetailService.getDetailByProjectIds(params);
    }
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/wmsProjectDetail/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= wmsProjectDetailService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/wmsProjectDetail/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        WmsProjectDetail model = wmsProjectDetailService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/wmsProjectDetail/save")
    public Result save(@RequestBody WmsProjectDetail wmsProjectDetail) {
        wmsProjectDetailService.saveOrUpdate(wmsProjectDetail);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/wmsProjectDetail/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<WmsProjectDetail>> map) {
        List<WmsProjectDetail> models = map.get("models");
        wmsProjectDetailService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/wmsProjectDetail/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        wmsProjectDetailService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/wmsProjectDetail/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<WmsProjectDetail> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, WmsProjectDetail.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        wmsProjectDetailService.save(u);
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
    @PostMapping("/wmsProjectDetail/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<WmsProjectDetail> wmsProjectDetails =new ArrayList<>();
        List<WmsProjectDetail> wmsProjectDetailList = wmsProjectDetailService.list(new QueryWrapper<WmsProjectDetail>().eq("cu_id", cuId));
        if (wmsProjectDetailList.isEmpty()) {wmsProjectDetails.add(wmsProjectDetailService.getById(0)); } else {
            for (WmsProjectDetail wmsProjectDetail : wmsProjectDetailList) {
                wmsProjectDetails.add(wmsProjectDetail);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(wmsProjectDetails, "工单明细表导出", "工单明细表导出", WmsProjectDetail.class, "wmsProjectDetail.xls", response);

    }
}
