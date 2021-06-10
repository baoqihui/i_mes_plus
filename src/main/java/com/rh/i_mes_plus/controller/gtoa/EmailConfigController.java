package com.rh.i_mes_plus.controller.gtoa;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.gtoa.EmailConfig;
import com.rh.i_mes_plus.service.gtoa.IEmailConfigService;
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
 * @date 2020-10-21 19:50:27
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "email配置")
@RequestMapping("ema")
public class EmailConfigController {
    @Autowired
    private IEmailConfigService emailConfigService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/emailConfig/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= emailConfigService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/emailConfig/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        EmailConfig model = emailConfigService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/emailConfig/save")
    public Result save(@RequestBody EmailConfig emailConfig) {
        emailConfigService.saveOrUpdate(emailConfig);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/emailConfig/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<EmailConfig>> map) {
        List<EmailConfig> models = map.get("models");
        emailConfigService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/emailConfig/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        emailConfigService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/emailConfig/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<EmailConfig> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, EmailConfig.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        emailConfigService.save(u);
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
    @PostMapping("/emailConfig/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<EmailConfig> emailConfigs =new ArrayList<>();
        List<EmailConfig> emailConfigList = emailConfigService.list(new QueryWrapper<EmailConfig>().eq("cu_id", cuId));
        if (emailConfigList.isEmpty()) {emailConfigs.add(emailConfigService.getById(0)); } else {
            for (EmailConfig emailConfig : emailConfigList) {
                emailConfigs.add(emailConfig);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(emailConfigs, "email配置导出", "email配置导出", EmailConfig.class, "emailConfig.xls", response);

    }
}
