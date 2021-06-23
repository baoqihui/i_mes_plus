package com.rh.i_mes_plus.controller.ums;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.ums.UmsSparesItemTypeInfo;
import com.rh.i_mes_plus.model.ums.UmsSparesTypeInfo;
import com.rh.i_mes_plus.service.ums.IUmsDepaService;
import com.rh.i_mes_plus.service.ums.IUmsSparesItemTypeInfoService;
import com.rh.i_mes_plus.service.ums.IUmsSparesTypeInfoService;
import com.rh.i_mes_plus.service.ums.IUmsUserService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 备品大类信息表
 *
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "备品大类信息表")
@RequestMapping("ums")
public class UmsSparesTypeInfoController {
    @Autowired
    private IUmsSparesTypeInfoService umsSparesTypeInfoService;
    @Autowired
    private IUmsSparesItemTypeInfoService umsSparesItemTypeInfoService;
    @Autowired
    private IUmsDepaService umsDepaService;
    /**
     * 根据typeCode查询备品分类列表
     */
    @ApiOperation(value = "根据typeCode查询备品分类列表")
    @PostMapping("/umsSparesTypeInfo/selBytypeCode/{typeCode}")
    public Result findTypeByDtCode(@PathVariable String typeCode) {
        UmsSparesTypeInfo umsSparesTypeInfo = umsSparesTypeInfoService.getOne(new QueryWrapper<UmsSparesTypeInfo>().eq("type_code", typeCode));
        String typeName = umsSparesTypeInfo.getTypeName();
        Map<String, Object> sparesType = new HashMap<>();
        sparesType.put("typeCode",typeCode);
        sparesType.put("typeName",typeName);
        List<Map<String, Object>> itemTypes=new ArrayList<>();
        List<UmsSparesItemTypeInfo> umsSparesItemTypeInfos = umsSparesItemTypeInfoService.list(new QueryWrapper<UmsSparesItemTypeInfo>().eq("type_code", typeCode));
        for (UmsSparesItemTypeInfo umsSparesItemTypeInfo : umsSparesItemTypeInfos) {
            Map<String, Object> itemType = new HashMap<>();
            String itemTypeCode = umsSparesItemTypeInfo.getItemTypeCode();
            String itemTypeName = umsSparesItemTypeInfo.getItemTypeName();
            itemType.put("itemTypeCode",itemTypeCode);
            itemType.put("itemTypeName",itemTypeName);
            itemType.put("itemCode",umsSparesItemTypeInfo.getItemCode());
            itemTypes.add(itemType);
        }
        sparesType.put("itemTypes",itemTypes);
        return Result.succeed(sparesType, "查询成功");
    }
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/umsSparesTypeInfo/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        String code = MapUtil.getStr(params, "depaCode");
        List<String> umsDepas = StrUtil.isNotEmpty(code)?umsDepaService.getSon(params):new ArrayList<>();
        params.put("umsDepas",umsDepas);
        Page<Map> list= umsSparesTypeInfoService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/umsSparesTypeInfo/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        UmsSparesTypeInfo model = umsSparesTypeInfoService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/umsSparesTypeInfo/save")
    public Result save(@RequestBody UmsSparesTypeInfo umsSparesTypeInfo) {
        umsSparesTypeInfoService.saveOrUpdate(umsSparesTypeInfo);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/umsSparesTypeInfo/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<UmsSparesTypeInfo>> map) {
        List<UmsSparesTypeInfo> models = map.get("models");
        umsSparesTypeInfoService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/umsSparesTypeInfo/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        umsSparesTypeInfoService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/umsSparesTypeInfo/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<UmsSparesTypeInfo> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, UmsSparesTypeInfo.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        umsSparesTypeInfoService.save(u);
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
    @PostMapping("/umsSparesTypeInfo/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<UmsSparesTypeInfo> umsSparesTypeInfos =new ArrayList<>();
        List<UmsSparesTypeInfo> umsSparesTypeInfoList = umsSparesTypeInfoService.list(new QueryWrapper<UmsSparesTypeInfo>().eq("cu_id", cuId));
        if (umsSparesTypeInfoList.isEmpty()) {umsSparesTypeInfos.add(umsSparesTypeInfoService.getById(0)); } else {
            for (UmsSparesTypeInfo umsSparesTypeInfo : umsSparesTypeInfoList) {
                umsSparesTypeInfos.add(umsSparesTypeInfo);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(umsSparesTypeInfos, "备品大类信息表导出", "备品大类信息表导出", UmsSparesTypeInfo.class, "umsSparesTypeInfo.xls", response);

    }
}
