package com.rh.i_mes_plus.controller.gtoa;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.gtoa.EmailLog;
import com.rh.i_mes_plus.service.gtoa.IEmailLogService;
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
 * 
 *
 * @author hbq
 * @date 2020-10-21 19:42:58
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "email日志")
@RequestMapping("ema")
public class EmailLogController {
    @Autowired
    private IEmailLogService emailLogService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/emailLog/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= emailLogService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/emailLog/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        EmailLog model = emailLogService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/emailLog/save")
    public Result save(@RequestBody EmailLog emailLog) {
        emailLogService.saveOrUpdate(emailLog);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/emailLog/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<EmailLog>> map) {
        List<EmailLog> models = map.get("models");
        emailLogService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/emailLog/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        emailLogService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/emailLog/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<EmailLog> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, EmailLog.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        emailLogService.save(u);
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
    @PostMapping("/emailLog/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<EmailLog> emailLogs =new ArrayList<>();
        List<EmailLog> emailLogList = emailLogService.list(new QueryWrapper<EmailLog>().eq("cu_id", cuId));
        if (emailLogList.isEmpty()) {emailLogs.add(emailLogService.getById(0)); } else {
            for (EmailLog emailLog : emailLogList) {
                emailLogs.add(emailLog);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(emailLogs, "email日志导出", "email日志导出", EmailLog.class, "emailLog.xls", response);

    }
}
