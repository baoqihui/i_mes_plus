package com.rh.i_mes_plus.controller.other;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.other.EccSendInfo;
import com.rh.i_mes_plus.service.other.IEccSendInfoService;
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
 * ECC扣料数据
 *
 * @author hbq
 * @date 2021-01-09 15:34:09
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "ECC扣料数据")
@RequestMapping("ecc")
public class EccSendInfoController {
    @Autowired
    private IEccSendInfoService eccSendInfoService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/eccSendInfo/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= eccSendInfoService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/eccSendInfo/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        EccSendInfo model = eccSendInfoService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/eccSendInfo/save")
    public Result save(@RequestBody EccSendInfo eccSendInfo) {
        eccSendInfoService.saveOrUpdate(eccSendInfo);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/eccSendInfo/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<EccSendInfo>> map) {
        List<EccSendInfo> models = map.get("models");
        eccSendInfoService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/eccSendInfo/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        eccSendInfoService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/eccSendInfo/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<EccSendInfo> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, EccSendInfo.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        eccSendInfoService.save(u);
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
    @PostMapping("/eccSendInfo/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<EccSendInfo> eccSendInfos =new ArrayList<>();
        List<EccSendInfo> eccSendInfoList = eccSendInfoService.list(new QueryWrapper<EccSendInfo>().eq("cu_id", cuId));
        if (eccSendInfoList.isEmpty()) {eccSendInfos.add(eccSendInfoService.getById(0)); } else {
            for (EccSendInfo eccSendInfo : eccSendInfoList) {
                eccSendInfos.add(eccSendInfo);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(eccSendInfos, "ECC扣料数据导出", "ECC扣料数据导出", EccSendInfo.class, "eccSendInfo.xls", response);

    }
}
