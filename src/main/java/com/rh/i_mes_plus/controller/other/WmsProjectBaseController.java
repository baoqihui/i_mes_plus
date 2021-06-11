package com.rh.i_mes_plus.controller.other;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.other.WmsProjectBase;
import com.rh.i_mes_plus.service.other.IWmsProjectBaseService;
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
 * 工单信息主表
 *
 * @author hbq
 * @date 2021-05-27 08:41:55
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "工单信息主表")
@RequestMapping("wms")
public class WmsProjectBaseController {
    @Autowired
    private IWmsProjectBaseService wmsProjectBaseService;
    @Autowired
    private IUmsDepaService umsDepaService;
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/wmsProjectBase/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        String code = MapUtil.getStr(params, "depaCode");
        List<String> umsDepas = StrUtil.isNotEmpty(code)?umsDepaService.getSon(params):new ArrayList<>();
        params.put("umsDepas",umsDepas);
        Page<Map> list= wmsProjectBaseService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/wmsProjectBase/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        WmsProjectBase model = wmsProjectBaseService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/wmsProjectBase/save")
    public Result save(@RequestBody WmsProjectBase wmsProjectBase) {
        wmsProjectBaseService.saveOrUpdate(wmsProjectBase);
        return Result.succeed("保存成功");
    }

    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/wmsProjectBase/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<WmsProjectBase>> map) {
        List<WmsProjectBase> models = map.get("models");
        wmsProjectBaseService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/wmsProjectBase/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        wmsProjectBaseService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/wmsProjectBase/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<WmsProjectBase> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, WmsProjectBase.class);
            rowNum = list.size();
            if (rowNum > 0) {
                list.forEach(u -> {
                        wmsProjectBaseService.save(u);
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
    @PostMapping("/wmsProjectBase/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<WmsProjectBase> wmsProjectBaseList = ids==null||ids.isEmpty()? wmsProjectBaseService.list():(List)wmsProjectBaseService.listByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(wmsProjectBaseList, "工单信息主表导出", "工单信息主表导出", WmsProjectBase.class, "wmsProjectBase.xls", response);
    }
}
