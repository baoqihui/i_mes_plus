package com.rh.i_mes_plus.controller.iqc;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.iqc.IqcOqaTestType;
import com.rh.i_mes_plus.service.iqc.IIqcOqaTestTypeService;
import com.rh.i_mes_plus.service.ums.IUmsDepaService;
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
 * 抽样方案
(正常、加严、放宽)
 *
 * @author hqb
 * @date 2020-10-16 11:39:51
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "抽样方案 (正常、加严、放宽)")
@RequestMapping("iqc")
public class IqcOqaTestTypeController {
    @Autowired
    private IIqcOqaTestTypeService iqcOqaTestTypeService;
    @Autowired
    private IUmsDepaService umsDepaService;
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/iqcOqaTestType/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        String code = MapUtil.getStr(params, "depaCode");
        List<String> umsDepas = StrUtil.isNotEmpty(code)?umsDepaService.getSon(params):new ArrayList<>();
        params.put("umsDepas",umsDepas);
        Page<IqcOqaTestType> list= iqcOqaTestTypeService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/iqcOqaTestType/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        IqcOqaTestType model = iqcOqaTestTypeService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/iqcOqaTestType/save")
    public Result save(@RequestBody IqcOqaTestType iqcOqaTestType) {
        String depaName = umsDepaService.getDepaNameByCode(iqcOqaTestType.getDepaCode());
        iqcOqaTestType.setDepaName(depaName);
        iqcOqaTestTypeService.saveOrUpdate(iqcOqaTestType);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/iqcOqaTestType/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        iqcOqaTestTypeService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/iqcOqaTestType/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<IqcOqaTestType> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, IqcOqaTestType.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        iqcOqaTestTypeService.save(u);
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
    @PostMapping("/iqcOqaTestType/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<IqcOqaTestType> iqcOqaTestTypes =new ArrayList<>();
        List<IqcOqaTestType> iqcOqaTestTypeList = iqcOqaTestTypeService.list(new QueryWrapper<IqcOqaTestType>().eq("cu_id", cuId));
        if (iqcOqaTestTypeList.isEmpty()) {iqcOqaTestTypes.add(iqcOqaTestTypeService.getById(0)); } else {
            for (IqcOqaTestType iqcOqaTestType : iqcOqaTestTypeList) {
                iqcOqaTestTypes.add(iqcOqaTestType);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(iqcOqaTestTypes, "抽样方案 (正常、加严、放宽)导出", "抽样方案 (正常、加严、放宽)导出", IqcOqaTestType.class, "iqcOqaTestType.xls", response);

    }
}
