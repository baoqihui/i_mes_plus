package com.rh.i_mes_plus.controller.pdt;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.PdtFeedingStationDTO;
import com.rh.i_mes_plus.model.pdt.PdtFeedingStation;
import com.rh.i_mes_plus.service.pdt.IPdtFeedingStationService;
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
 * 料站表主表
 *
 * @author hbq
 * @date 2021-05-24 10:17:57
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "料站表主表")
@RequestMapping("pdt")
public class PdtFeedingStationController {
    @Autowired
    private IPdtFeedingStationService pdtFeedingStationService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/pdtFeedingStation/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= pdtFeedingStationService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/pdtFeedingStation/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        PdtFeedingStation model = pdtFeedingStationService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/pdtFeedingStation/save")
    public Result save(@RequestBody PdtFeedingStation pdtFeedingStation) {
        pdtFeedingStationService.saveOrUpdate(pdtFeedingStation);
        return Result.succeed("保存成功");
    }

    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/pdtFeedingStation/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<PdtFeedingStation>> map) {
        List<PdtFeedingStation> models = map.get("models");
        pdtFeedingStationService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 新增(包含详情)
     */
    @ApiOperation(value = "新增(包含详情)")
    @PostMapping("/pdtFeedingStation/saveAll")
    public Result saveAll(@RequestBody PdtFeedingStationDTO pdtFeedingStationDTO) {
        return pdtFeedingStationService.saveAll(pdtFeedingStationDTO);
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/pdtFeedingStation/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        pdtFeedingStationService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/pdtFeedingStation/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<PdtFeedingStation> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, PdtFeedingStation.class);
            rowNum = list.size();
            if (rowNum > 0) {
                list.forEach(u -> {
                        pdtFeedingStationService.save(u);
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
    @PostMapping("/pdtFeedingStation/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<PdtFeedingStation> pdtFeedingStationList = ids==null||ids.isEmpty()? pdtFeedingStationService.list():(List)pdtFeedingStationService.listByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(pdtFeedingStationList, "料站表主表导出", "料站表主表导出", PdtFeedingStation.class, "pdtFeedingStation.xls", response);
    }
}
