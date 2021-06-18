package com.rh.i_mes_plus.controller.pdt;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.pdt.PdtWmsReceiveDetailSub;
import com.rh.i_mes_plus.service.pdt.IPdtWmsReceiveDetailSubService;
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
public class PdtWmsReceiveDetailSubController {
    @Autowired
    private IPdtWmsReceiveDetailSubService pdtWmsReceiveDetailSubService;

    @ApiOperation(value = "入库明细查询")
    @PostMapping("/pdtWmsReceiveDetailSub/listAll")
    public Result<PageResult> listAll(@RequestBody Map<String, Object> params) {
        Page<Map> list= pdtWmsReceiveDetailSubService.listAll(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    @ApiOperation(value = "入库汇总查询")
    @PostMapping("/pdtWmsReceiveDetailSub/listAllCollect")
    public Result<PageResult> listAllCollect(@RequestBody Map<String, Object> params) {
        Page<Map> list= pdtWmsReceiveDetailSubService.listAllCollect(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    @ApiOperation(value = "入库箱号汇总查询")
    @PostMapping("/pdtWmsReceiveDetailSub/listAllCollectByBoxNo")
    public Result<PageResult> listAllCollectByBoxNo(@RequestBody Map<String, Object> params) {
        Page<Map> list= pdtWmsReceiveDetailSubService.listAllCollectByBoxNo(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/pdtWmsReceiveDetailSub/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= pdtWmsReceiveDetailSubService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/pdtWmsReceiveDetailSub/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        PdtWmsReceiveDetailSub model = pdtWmsReceiveDetailSubService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/pdtWmsReceiveDetailSub/save")
    public Result save(@RequestBody PdtWmsReceiveDetailSub pdtWmsReceiveDetailSub) {
        pdtWmsReceiveDetailSubService.saveOrUpdate(pdtWmsReceiveDetailSub);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/pdtWmsReceiveDetailSub/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<PdtWmsReceiveDetailSub>> map) {
        List<PdtWmsReceiveDetailSub> models = map.get("models");
        pdtWmsReceiveDetailSubService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/pdtWmsReceiveDetailSub/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        pdtWmsReceiveDetailSubService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/pdtWmsReceiveDetailSub/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<PdtWmsReceiveDetailSub> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, PdtWmsReceiveDetailSub.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        pdtWmsReceiveDetailSubService.save(u);
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
    @PostMapping("/pdtWmsReceiveDetailSub/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<PdtWmsReceiveDetailSub> pdtWmsReceiveDetailSubs =new ArrayList<>();
        List<PdtWmsReceiveDetailSub> pdtWmsReceiveDetailSubList = pdtWmsReceiveDetailSubService.list(new QueryWrapper<PdtWmsReceiveDetailSub>().eq("cu_id", cuId));
        if (pdtWmsReceiveDetailSubList.isEmpty()) {pdtWmsReceiveDetailSubs.add(pdtWmsReceiveDetailSubService.getById(0)); } else {
            for (PdtWmsReceiveDetailSub pdtWmsReceiveDetailSub : pdtWmsReceiveDetailSubList) {
                pdtWmsReceiveDetailSubs.add(pdtWmsReceiveDetailSub);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(pdtWmsReceiveDetailSubs, "入库单明细表导出", "入库单明细表导出", PdtWmsReceiveDetailSub.class, "pdtWmsReceiveDetailSub.xls", response);

    }
}
