package com.rh.i_mes_plus.controller.sps;
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
import com.rh.i_mes_plus.model.sps.TinReturnRecord;
import com.rh.i_mes_plus.service.sps.ITinReturnRecordService;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;

/**
 * 红锡膏退仓记录
 *
 * @author hbq
 * @date 2021-07-08 19:48:01
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "红锡膏退仓记录")
@RequestMapping("tin")
public class TinReturnRecordController {
    @Autowired
    private ITinReturnRecordService tinReturnRecordService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/tinReturnRecord/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= tinReturnRecordService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/tinReturnRecord/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        TinReturnRecord model = tinReturnRecordService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/tinReturnRecord/save")
    public Result save(@RequestBody TinReturnRecord tinReturnRecord) {
        tinReturnRecordService.saveOrUpdate(tinReturnRecord);
        return Result.succeed("保存成功");
    }

    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/tinReturnRecord/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<TinReturnRecord>> map) {
        List<TinReturnRecord> models = map.get("models");
        tinReturnRecordService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/tinReturnRecord/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        tinReturnRecordService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/tinReturnRecord/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<TinReturnRecord> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, TinReturnRecord.class);
            rowNum = list.size();
            if (rowNum > 0) {
                list.forEach(u -> {
                        tinReturnRecordService.save(u);
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
    @PostMapping("/tinReturnRecord/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<TinReturnRecord> tinReturnRecordList = ids==null||ids.isEmpty()? tinReturnRecordService.list():(List)tinReturnRecordService.listByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(tinReturnRecordList, "红锡膏退仓记录导出", "红锡膏退仓记录导出", TinReturnRecord.class, "tinReturnRecord.xls", response);
    }
}
