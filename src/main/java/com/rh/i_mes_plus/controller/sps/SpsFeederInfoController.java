package com.rh.i_mes_plus.controller.sps;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sps.SpsFeederInfo;
import com.rh.i_mes_plus.service.sps.ISpsFeederInfoService;
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
 * 料枪台账
 *
 * @author hbq
 * @date 2021-05-21 15:02:34
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "料枪台账")
@RequestMapping("sps")
public class SpsFeederInfoController {
    @Autowired
    private ISpsFeederInfoService spsFeederInfoService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/spsFeederInfo/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= spsFeederInfoService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/spsFeederInfo/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SpsFeederInfo model = spsFeederInfoService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/spsFeederInfo/save")
    public Result save(@RequestBody SpsFeederInfo spsFeederInfo) {
        spsFeederInfoService.saveOrUpdate(spsFeederInfo);
        return Result.succeed("保存成功");
    }

    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/spsFeederInfo/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<SpsFeederInfo>> map) {
        List<SpsFeederInfo> models = map.get("models");
        spsFeederInfoService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/spsFeederInfo/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        spsFeederInfoService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/spsFeederInfo/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SpsFeederInfo> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SpsFeederInfo.class);
            rowNum = list.size();
            if (rowNum > 0) {
                list.forEach(u -> {
                        spsFeederInfoService.save(u);
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
    @PostMapping("/spsFeederInfo/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<SpsFeederInfo> spsFeederInfoList = ids==null||ids.isEmpty()? spsFeederInfoService.list():(List)spsFeederInfoService.listByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(spsFeederInfoList, "料站表详情导出", "料站表详情导出", SpsFeederInfo.class, "spsFeederInfo.xls", response);
    }
}
