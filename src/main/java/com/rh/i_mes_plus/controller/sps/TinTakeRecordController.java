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
import com.rh.i_mes_plus.model.sps.TinTakeRecord;
import com.rh.i_mes_plus.service.sps.ITinTakeRecordService;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;

/**
 * 
 *
 * @author hbq
 * @date 2021-07-08 19:48:01
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "回温记录")
@RequestMapping("tin")
public class TinTakeRecordController {
    @Autowired
    private ITinTakeRecordService tinTakeRecordService;

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/tinTakeRecord/take")
    public Result take(@RequestBody Map<String, Object> params) {
        return tinTakeRecordService.take(params);
    }
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/tinTakeRecord/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= tinTakeRecordService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/tinTakeRecord/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        TinTakeRecord model = tinTakeRecordService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/tinTakeRecord/save")
    public Result save(@RequestBody TinTakeRecord tinTakeRecord) {
        tinTakeRecordService.saveOrUpdate(tinTakeRecord);
        return Result.succeed("保存成功");
    }

    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/tinTakeRecord/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<TinTakeRecord>> map) {
        List<TinTakeRecord> models = map.get("models");
        tinTakeRecordService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/tinTakeRecord/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        tinTakeRecordService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/tinTakeRecord/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<TinTakeRecord> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, TinTakeRecord.class);
            rowNum = list.size();
            if (rowNum > 0) {
                list.forEach(u -> {
                        tinTakeRecordService.save(u);
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
    @PostMapping("/tinTakeRecord/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<TinTakeRecord> tinTakeRecordList = ids==null||ids.isEmpty()? tinTakeRecordService.list():(List)tinTakeRecordService.listByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(tinTakeRecordList, "导出", "导出", TinTakeRecord.class, "tinTakeRecord.xls", response);
    }
}
