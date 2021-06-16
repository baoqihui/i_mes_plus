package com.rh.i_mes_plus.controller.sms;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sms.SmsWmsErpDocType;
import com.rh.i_mes_plus.model.sms.SmsWmsIoType;
import com.rh.i_mes_plus.service.sms.ISmsWmsErpDocTypeService;
import com.rh.i_mes_plus.service.sms.ISmsWmsIoTypeService;
import com.rh.i_mes_plus.util.EasyPoiExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 单据类型表
 *
 * @author hbq
 * @date 2021-01-19 09:52:44
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "单据类型表")
@RequestMapping("sms")
public class SmsWmsIoTypeController {
    @Autowired
    private ISmsWmsIoTypeService smsWmsIoTypeService;
    @Value("${zhaoIpAndPort}")
    private String zhaoIpAndPort;
    @Value("${liIpAndPort}")
    private String liIpAndPort;
    @Autowired
    private ISmsWmsErpDocTypeService smsWmsErpDocTypeService;

    @ApiOperation(value = "亮灯")
    @PostMapping(value = "/LightControl")
    protected Result ApiEcnCheckFai(@RequestBody List<Map<String, Object>> params)  {
        String result= HttpRequest.post(liIpAndPort+"/api/Light/LightControl").body(JSON.toJSONString(params)).execute().body();
        System.out.println(result);
        return Result.succeed(result,"成功");
    }
    @ApiOperation(value = "获取看板信息1")
    @GetMapping(value = "/ApiKanbanGetFaultInfo")
    protected Result GetFaultInfo()  {
        String result= HttpUtil.get(zhaoIpAndPort+"/ApiKanbanGetFaultInfo");
        JSONObject jsonObject = JSONUtil.parseObj(result);
        return Result.succeed(jsonObject,"成功");
    }

    @ApiOperation(value = "获取看板信息2")
    @GetMapping(value = "/ApiKanbanGetCapacityInfo")
    protected Result GetCapacityInfo()  {
        String result= HttpUtil.get(zhaoIpAndPort+"/ApiKanbanGetCapacityInfo");
        JSONObject jsonObject = JSONUtil.parseObj(result);
        return Result.succeed(jsonObject,"成功");
    }
    @ApiOperation(value = "获取看板信息3")
    @GetMapping(value = "/ApiKanbanGetSmtEffictive")
    protected Result ApiGetSmtEffictive()  {
        String result= HttpUtil.get(zhaoIpAndPort+"/ApiKanbanGetSmtEffictive");
        JSONObject jsonObject = JSONUtil.parseObj(result);
        return Result.succeed(jsonObject,"成功");
    }
    @ApiOperation(value = "获取看板信息4")
    @GetMapping(value = "/ApiKanbanGetSchedule")
    protected Result ApiGetSchedule()  {
        String result= HttpUtil.get(zhaoIpAndPort+"/ApiKanbanGetSchedule");
        JSONObject jsonObject = JSONUtil.parseObj(result);
        return Result.succeed(jsonObject,"成功");
    }
    @ApiOperation(value = "获取看板信息5")
    @GetMapping(value = "/ApiKanbanGetSales")
    protected Result ApiGetStock()  {
        String result= HttpUtil.get(zhaoIpAndPort+"/ApiKanbanGetSales");
        JSONObject jsonObject = JSONUtil.parseObj(result);
        return Result.succeed(jsonObject,"成功");
    }
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/smsWmsIoType/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= smsWmsIoTypeService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    /**
     * 根据dtCode查询收发类型列表
     */
    @ApiOperation(value = "根据dtCode查询收发类型列表")
    @PostMapping("/smsWmsIoType/sel/{dtCode}")
    public Result findTypeByDtCode(@PathVariable String dtCode) {
        SmsWmsIoType ioType = smsWmsIoTypeService.getOne(new QueryWrapper<SmsWmsIoType>().eq("dt_code", dtCode));
        String dtName = ioType.getDtName();
        Map<String, Object> ioTypeMap = new HashMap<>();
        ioTypeMap.put("dtCode",dtCode);
        ioTypeMap.put("dtName",dtName);
        List<Map<String, Object>> erpDocTypes=new ArrayList<>();
        List<SmsWmsErpDocType> erpDocTypes1 = smsWmsErpDocTypeService.list(new QueryWrapper<SmsWmsErpDocType>().eq("dt_code", dtCode));
        for (SmsWmsErpDocType erpDocType : erpDocTypes1) {
            Long dId = erpDocType.getDId();
            String typeName = dtName+"-"+erpDocType.getTypeName();
            Map<String, Object> erpDocTypeMap = new HashMap<>();
            erpDocTypeMap.put("dId",dId);
            erpDocTypeMap.put("typeName",typeName);
            erpDocTypes.add(erpDocTypeMap);
        }
        ioTypeMap.put("erpDocTypes",erpDocTypes);
        return Result.succeed(ioTypeMap, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/smsWmsIoType/save")
    public Result save(@RequestBody SmsWmsIoType smsWmsIoType) {
        smsWmsIoTypeService.saveOrUpdate(smsWmsIoType);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/smsWmsIoType/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<SmsWmsIoType>> map) {
        List<SmsWmsIoType> models = map.get("models");
        smsWmsIoTypeService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/smsWmsIoType/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        smsWmsIoTypeService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/smsWmsIoType/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<SmsWmsIoType> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, SmsWmsIoType.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        smsWmsIoTypeService.save(u);
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
    @PostMapping("/smsWmsIoType/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<SmsWmsIoType> smsWmsIoTypes =new ArrayList<>();
        List<SmsWmsIoType> smsWmsIoTypeList = smsWmsIoTypeService.list(new QueryWrapper<SmsWmsIoType>().eq("cu_id", cuId));
        if (smsWmsIoTypeList.isEmpty()) {smsWmsIoTypes.add(smsWmsIoTypeService.getById(0)); } else {
            for (SmsWmsIoType smsWmsIoType : smsWmsIoTypeList) {
                smsWmsIoTypes.add(smsWmsIoType);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(smsWmsIoTypes, "单据类型表导出", "单据类型表导出", SmsWmsIoType.class, "smsWmsIoType.xls", response);

    }
}
