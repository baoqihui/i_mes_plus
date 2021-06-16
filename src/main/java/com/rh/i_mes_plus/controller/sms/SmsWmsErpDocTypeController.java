package com.rh.i_mes_plus.controller.sms;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sms.SmsWmsErpDocType;
import com.rh.i_mes_plus.service.sms.ISmsWmsErpDocTypeService;
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
 * ERP单据性质档
 *
 * @author hbq
 * @date 2021-01-19 09:52:44
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "ERP单据性质档")
@RequestMapping("sms")
public class SmsWmsErpDocTypeController {
    @Autowired
    private ISmsWmsErpDocTypeService smsWmsErpDocTypeService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/smsWmsErpDocType/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= smsWmsErpDocTypeService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/smsWmsErpDocType/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        SmsWmsErpDocType model = smsWmsErpDocTypeService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/smsWmsErpDocType/save")
    public Result save(@RequestBody SmsWmsErpDocType smsWmsErpDocType) {
        smsWmsErpDocTypeService.saveOrUpdate(smsWmsErpDocType);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/smsWmsErpDocType/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<SmsWmsErpDocType>> map) {
        List<SmsWmsErpDocType> models = map.get("models");
        smsWmsErpDocTypeService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/smsWmsErpDocType/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        smsWmsErpDocTypeService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/smsWmsErpDocType/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SmsWmsErpDocType> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SmsWmsErpDocType.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        smsWmsErpDocTypeService.save(u);
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
    @PostMapping("/smsWmsErpDocType/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<SmsWmsErpDocType> smsWmsErpDocTypes =new ArrayList<>();
        List<SmsWmsErpDocType> smsWmsErpDocTypeList = smsWmsErpDocTypeService.list(new QueryWrapper<SmsWmsErpDocType>().eq("cu_id", cuId));
        if (smsWmsErpDocTypeList.isEmpty()) {smsWmsErpDocTypes.add(smsWmsErpDocTypeService.getById(0)); } else {
            for (SmsWmsErpDocType smsWmsErpDocType : smsWmsErpDocTypeList) {
                smsWmsErpDocTypes.add(smsWmsErpDocType);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(smsWmsErpDocTypes, "ERP单据性质档导出", "ERP单据性质档导出", SmsWmsErpDocType.class, "smsWmsErpDocType.xls", response);

    }
}
