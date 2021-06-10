package com.rh.i_mes_plus.controller.gtoa;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.gtoa.EcnDetailDiscriptionInfo;
import com.rh.i_mes_plus.service.gtoa.IEcnDetailDiscriptionInfoService;
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
 * @date 2020-10-22 19:36:56
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "ecn变更描述")
@RequestMapping("ecn")
public class EcnDetailDiscriptionInfoController {
    @Autowired
    private IEcnDetailDiscriptionInfoService ecnDetailDiscriptionInfoService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/ecnDetailDiscriptionInfo/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= ecnDetailDiscriptionInfoService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/ecnDetailDiscriptionInfo/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        EcnDetailDiscriptionInfo model = ecnDetailDiscriptionInfoService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/ecnDetailDiscriptionInfo/save")
    public Result save(@RequestBody EcnDetailDiscriptionInfo ecnDetailDiscriptionInfo) {
        ecnDetailDiscriptionInfoService.saveOrUpdate(ecnDetailDiscriptionInfo);
        return Result.succeed("保存成功");
    }

    /**
     * QA检验
     */
    @ApiOperation(value = "QA检验")
    @PostMapping("/ecnDetailDiscriptionInfo/QAcheck")
    public Result QAcheck(@RequestBody Map<String,List<EcnDetailDiscriptionInfo>> map) {
        return ecnDetailDiscriptionInfoService.QAcheck(map);
    }
    /**
     * QA手动审核通过
     */
    @ApiOperation(value = "QA手动审核通过")
    @PostMapping("/ecnDetailDiscriptionInfo/QAcheckSuccess")
    public Result QAcheckSuccess(@RequestBody Map<String,List<EcnDetailDiscriptionInfo>> map) {
        return ecnDetailDiscriptionInfoService.QAcheckSuccess(map);
    }
    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/ecnDetailDiscriptionInfo/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        ecnDetailDiscriptionInfoService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/ecnDetailDiscriptionInfo/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<EcnDetailDiscriptionInfo> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, EcnDetailDiscriptionInfo.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        ecnDetailDiscriptionInfoService.save(u);
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
    @PostMapping("/ecnDetailDiscriptionInfo/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<EcnDetailDiscriptionInfo> ecnDetailDiscriptionInfos =new ArrayList<>();
        List<EcnDetailDiscriptionInfo> ecnDetailDiscriptionInfoList = ecnDetailDiscriptionInfoService.list(new QueryWrapper<EcnDetailDiscriptionInfo>().eq("cu_id", cuId));
        if (ecnDetailDiscriptionInfoList.isEmpty()) {ecnDetailDiscriptionInfos.add(ecnDetailDiscriptionInfoService.getById(0)); } else {
            for (EcnDetailDiscriptionInfo ecnDetailDiscriptionInfo : ecnDetailDiscriptionInfoList) {
                ecnDetailDiscriptionInfos.add(ecnDetailDiscriptionInfo);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(ecnDetailDiscriptionInfos, "ecn变更描述导出", "ecn变更描述导出", EcnDetailDiscriptionInfo.class, "ecnDetailDiscriptionInfo.xls", response);

    }
}
