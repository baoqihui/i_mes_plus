package com.rh.i_mes_plus.controller.pdt;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.pdt.PdtWmsReceiveDetail;
import com.rh.i_mes_plus.service.pdt.IPdtWmsReceiveDetailService;
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
 * 入库单明细表
 *
 * @author hbq
 * @date 2020-12-29 15:41:49
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "成品入库单明细表")
@RequestMapping("pdt")
public class PdtWmsReceiveDetailController {
    @Autowired
    private IPdtWmsReceiveDetailService pdtWmsReceiveDetailService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/pdtWmsReceiveDetail/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= pdtWmsReceiveDetailService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/pdtWmsReceiveDetail/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        PdtWmsReceiveDetail model = pdtWmsReceiveDetailService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/pdtWmsReceiveDetail/save")
    public Result save(@RequestBody PdtWmsReceiveDetail pdtWmsReceiveDetail) {
        pdtWmsReceiveDetailService.saveOrUpdate(pdtWmsReceiveDetail);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/pdtWmsReceiveDetail/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<PdtWmsReceiveDetail>> map) {
        List<PdtWmsReceiveDetail> models = map.get("models");
        pdtWmsReceiveDetailService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/pdtWmsReceiveDetail/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        pdtWmsReceiveDetailService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/pdtWmsReceiveDetail/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<PdtWmsReceiveDetail> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, PdtWmsReceiveDetail.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        pdtWmsReceiveDetailService.save(u);
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
    @PostMapping("/pdtWmsReceiveDetail/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<PdtWmsReceiveDetail> pdtWmsReceiveDetails =new ArrayList<>();
        List<PdtWmsReceiveDetail> pdtWmsReceiveDetailList = pdtWmsReceiveDetailService.list(new QueryWrapper<PdtWmsReceiveDetail>().eq("cu_id", cuId));
        if (pdtWmsReceiveDetailList.isEmpty()) {pdtWmsReceiveDetails.add(pdtWmsReceiveDetailService.getById(0)); } else {
            for (PdtWmsReceiveDetail pdtWmsReceiveDetail : pdtWmsReceiveDetailList) {
                pdtWmsReceiveDetails.add(pdtWmsReceiveDetail);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(pdtWmsReceiveDetails, "入库单明细表导出", "入库单明细表导出", PdtWmsReceiveDetail.class, "pdtWmsReceiveDetail.xls", response);

    }
}
