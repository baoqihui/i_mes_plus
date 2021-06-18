package com.rh.i_mes_plus.controller.pdt;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.pdt.PdtBomDetail;
import com.rh.i_mes_plus.service.pdt.IPdtBomDetailService;
import com.rh.i_mes_plus.util.EasyPoiExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author hbq
 * @date 2021-05-25 13:18:22
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "bom详情")
@RequestMapping("pdt")
public class PdtBomDetailController {
    @Autowired
    private IPdtBomDetailService pdtBomDetailService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/pdtBomDetail/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= pdtBomDetailService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/pdtBomDetail/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        PdtBomDetail model = pdtBomDetailService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/pdtBomDetail/save")
    public Result save(@RequestBody PdtBomDetail pdtBomDetail) {
        pdtBomDetailService.saveOrUpdate(pdtBomDetail);
        return Result.succeed("保存成功");
    }

    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/pdtBomDetail/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<PdtBomDetail>> map) {
        List<PdtBomDetail> models = map.get("models");
        pdtBomDetailService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/pdtBomDetail/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        pdtBomDetailService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/pdtBomDetail/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<PdtBomDetail> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, PdtBomDetail.class);
            rowNum = list.size();
            if (rowNum > 0) {
                list.forEach(u -> {
                        pdtBomDetailService.save(u);
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
    @PostMapping("/pdtBomDetail/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<PdtBomDetail> pdtBomDetailList = ids==null||ids.isEmpty()? pdtBomDetailService.list():(List)pdtBomDetailService.listByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(pdtBomDetailList, "导出", "导出", PdtBomDetail.class, "pdtBomDetail.xls", response);
    }
}
