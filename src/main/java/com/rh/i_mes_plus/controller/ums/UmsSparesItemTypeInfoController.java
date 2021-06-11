package com.rh.i_mes_plus.controller.ums;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.ums.UmsSparesItemTypeInfo;
import com.rh.i_mes_plus.service.ums.IUmsSparesItemTypeInfoService;
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
 * 备品类别信息表
 *
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "备品类别信息表")
@RequestMapping("ums")
public class UmsSparesItemTypeInfoController {
    @Autowired
    private IUmsSparesItemTypeInfoService umsSparesItemTypeInfoService;

    /**
     * 根据类别代码查询要生成的小类代码
     */
    @ApiOperation(value = "根据类别代码查询要生成的小类代码")
    @PostMapping("/umsSparesItemTypeInfo/getItemTypeCodeByTypeCode")
    public Result getDocNo(@RequestBody Map<String,Object> map) {
        return umsSparesItemTypeInfoService.getItemTypeCodeByTypeCode(map);
    }

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/umsSparesItemTypeInfo/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= umsSparesItemTypeInfoService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/umsSparesItemTypeInfo/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        UmsSparesItemTypeInfo model = umsSparesItemTypeInfoService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/umsSparesItemTypeInfo/save")
    public Result save(@RequestBody UmsSparesItemTypeInfo umsSparesItemTypeInfo) {
        umsSparesItemTypeInfoService.saveOrUpdate(umsSparesItemTypeInfo);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/umsSparesItemTypeInfo/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<UmsSparesItemTypeInfo>> map) {
        List<UmsSparesItemTypeInfo> models = map.get("models");
        umsSparesItemTypeInfoService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/umsSparesItemTypeInfo/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        umsSparesItemTypeInfoService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/umsSparesItemTypeInfo/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<UmsSparesItemTypeInfo> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, UmsSparesItemTypeInfo.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        umsSparesItemTypeInfoService.save(u);
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
    @PostMapping("/umsSparesItemTypeInfo/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<UmsSparesItemTypeInfo> umsSparesItemTypeInfos =new ArrayList<>();
        List<UmsSparesItemTypeInfo> umsSparesItemTypeInfoList = umsSparesItemTypeInfoService.list(new QueryWrapper<UmsSparesItemTypeInfo>().eq("cu_id", cuId));
        if (umsSparesItemTypeInfoList.isEmpty()) {umsSparesItemTypeInfos.add(umsSparesItemTypeInfoService.getById(0)); } else {
            for (UmsSparesItemTypeInfo umsSparesItemTypeInfo : umsSparesItemTypeInfoList) {
                umsSparesItemTypeInfos.add(umsSparesItemTypeInfo);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(umsSparesItemTypeInfos, "备品类别信息表导出", "备品类别信息表导出", UmsSparesItemTypeInfo.class, "umsSparesItemTypeInfo.xls", response);

    }
}
