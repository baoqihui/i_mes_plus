package com.rh.i_mes_plus.controller.pdt;
import java.io.IOException;
import java.util.List;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;


import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import com.rh.i_mes_plus.util.EasyPoiExcelUtil;
import com.rh.i_mes_plus.model.pdt.PdtMoFeedingStationDetail;
import com.rh.i_mes_plus.service.pdt.IPdtMoFeedingStationDetailService;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;

/**
 * 
 *
 * @author hbq
 * @date 2021-07-06 13:52:46
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "")
@RequestMapping("pdt")
public class PdtMoFeedingStationDetailController {
    @Autowired
    private IPdtMoFeedingStationDetailService pdtMoFeedingStationDetailService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/pdtMoFeedingStationDetail/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= pdtMoFeedingStationDetailService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/pdtMoFeedingStationDetail/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        PdtMoFeedingStationDetail model = pdtMoFeedingStationDetailService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/pdtMoFeedingStationDetail/save")
    public Result save(@RequestBody PdtMoFeedingStationDetail pdtMoFeedingStationDetail) {
        pdtMoFeedingStationDetailService.saveOrUpdate(pdtMoFeedingStationDetail);
        return Result.succeed("保存成功");
    }

    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/pdtMoFeedingStationDetail/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<PdtMoFeedingStationDetail>> map) {
        List<PdtMoFeedingStationDetail> models = map.get("models");
        pdtMoFeedingStationDetailService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/pdtMoFeedingStationDetail/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        pdtMoFeedingStationDetailService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/pdtMoFeedingStationDetail/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<PdtMoFeedingStationDetail> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, PdtMoFeedingStationDetail.class);
            rowNum = list.size();
            if (rowNum > 0) {
                list.forEach(u -> {
                        pdtMoFeedingStationDetailService.save(u);
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
    @PostMapping("/pdtMoFeedingStationDetail/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<PdtMoFeedingStationDetail> pdtMoFeedingStationDetailList = ids==null||ids.isEmpty()? pdtMoFeedingStationDetailService.list():(List)pdtMoFeedingStationDetailService.listByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(pdtMoFeedingStationDetailList, "导出", "导出", PdtMoFeedingStationDetail.class, "pdtMoFeedingStationDetail.xls", response);
    }
}
