package com.rh.i_mes_plus.controller.pdt;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.PdtBomDTO;
import com.rh.i_mes_plus.model.pdt.PdtBom;
import com.rh.i_mes_plus.model.pdt.PdtBomDetail;
import com.rh.i_mes_plus.service.pdt.IPdtBomDetailService;
import com.rh.i_mes_plus.service.pdt.IPdtBomService;
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
 * bom表
 *
 * @author hbq
 * @date 2021-05-25 13:18:22
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "bom表")
@RequestMapping("pdt")
public class PdtBomController {
    @Autowired
    private IPdtBomService pdtBomService;
    @Autowired
    private IPdtBomDetailService pdtBomDetailService;

    @ApiOperation(value = "输入机种号查询当前下级")
    @PostMapping("/pdtBom/getListByItemCode")
    public Result getListByItemCode(@RequestBody Map<String, Object> params) {
        return pdtBomService.getListByItemCode(params);
    }
    @ApiOperation(value = "输入机种号查询当前下级的版本的pcb")
    @PostMapping("/pdtBom/getPcbMapByItemCode")
    public Result getPcbMapByItemCode(@RequestBody Map<String, Object> params) {
        return pdtBomService.getPcbMapByItemCode(params);
    }
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/pdtBom/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= pdtBomService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/pdtBom/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        PdtBom model = pdtBomService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 转量产
     */
    @ApiOperation(value = "转量产")
    @PostMapping("/pdtBom/changeStage")
    public Result changeStage(@RequestBody PdtBom pdtBom) {
        return pdtBomService.changeStage(pdtBom);
    }
    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/pdtBom/save")
    public Result save(@RequestBody PdtBom pdtBom) {
        pdtBomService.saveOrUpdate(pdtBom);
        return Result.succeed("保存成功");
    }

    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/pdtBom/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<PdtBom>> map) {
        List<PdtBom> models = map.get("models");
        pdtBomService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 新增(包含详情)
     */
    @ApiOperation(value = "新增(包含详情)")
    @PostMapping("/pdtBom/saveAll")
    public Result saveAll(@RequestBody PdtBomDTO pdtBomDTO) {
        return pdtBomService.saveAll(pdtBomDTO);
    }
    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/pdtBom/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        for (Long id : ids) {
            pdtBomDetailService.remove(new LambdaQueryWrapper<PdtBomDetail>().eq(PdtBomDetail::getParentId,id));
        }
        pdtBomService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/pdtBom/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<PdtBom> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, PdtBom.class);
            rowNum = list.size();
            if (rowNum > 0) {
                list.forEach(u -> {
                        pdtBomService.save(u);
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
    @PostMapping("/pdtBom/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<PdtBom> pdtBomList = ids==null||ids.isEmpty()? pdtBomService.list():(List)pdtBomService.listByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(pdtBomList, "bom表导出", "bom表导出", PdtBom.class, "pdtBom.xls", response);
    }
}



