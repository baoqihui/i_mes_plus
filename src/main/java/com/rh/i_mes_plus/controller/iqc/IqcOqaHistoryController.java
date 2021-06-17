package com.rh.i_mes_plus.controller.iqc;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.iqc.IqcOqaHistory;
import com.rh.i_mes_plus.service.iqc.IIqcOqaHistoryService;
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
 * OQA抽检历史记录
 *
 * @author hbq
 * @date 2020-10-22 16:28:19
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "OQA抽检历史记录")
@RequestMapping("iqc")
public class IqcOqaHistoryController {
    @Autowired
    private IIqcOqaHistoryService iqcOqaHistoryService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/iqcOqaHistory/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= iqcOqaHistoryService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/iqcOqaHistory/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        IqcOqaHistory model = iqcOqaHistoryService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/iqcOqaHistory/save")
    public Result save(@RequestBody IqcOqaHistory iqcOqaHistory) {
        iqcOqaHistoryService.saveOrUpdate(iqcOqaHistory);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/iqcOqaHistory/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<IqcOqaHistory>> map) {
        List<IqcOqaHistory> models = map.get("models");
        iqcOqaHistoryService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/iqcOqaHistory/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        iqcOqaHistoryService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/iqcOqaHistory/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<IqcOqaHistory> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, IqcOqaHistory.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        iqcOqaHistoryService.save(u);
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
    @PostMapping("/iqcOqaHistory/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<IqcOqaHistory> iqcOqaHistorys =new ArrayList<>();
        List<IqcOqaHistory> iqcOqaHistoryList = iqcOqaHistoryService.list(new QueryWrapper<IqcOqaHistory>().eq("cu_id", cuId));
        if (iqcOqaHistoryList.isEmpty()) {iqcOqaHistorys.add(iqcOqaHistoryService.getById(0)); } else {
            for (IqcOqaHistory iqcOqaHistory : iqcOqaHistoryList) {
                iqcOqaHistorys.add(iqcOqaHistory);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(iqcOqaHistorys, "OQA抽检历史记录导出", "OQA抽检历史记录导出", IqcOqaHistory.class, "iqcOqaHistory.xls", response);

    }
}
