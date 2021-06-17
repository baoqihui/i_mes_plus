package com.rh.i_mes_plus.controller.iqc;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.iqc.IqcOqaLevel;
import com.rh.i_mes_plus.service.iqc.IIqcOqaLevelService;
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
 * 检验水准
 *
 * @author hqb
 * @date 2020-10-16 11:39:51
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "检验水准")
@RequestMapping("iqc")
public class IqcOqaLevelController {
    @Autowired
    private IIqcOqaLevelService iqcOqaLevelService;
    @Autowired
    private IUmsDepaService umsDepaService;
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/iqcOqaLevel/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        String code = MapUtil.getStr(params, "depaCode");
        List<String> umsDepas = StrUtil.isNotEmpty(code)?umsDepaService.getSon(params):new ArrayList<>();
        params.put("umsDepas",umsDepas);
        Page<IqcOqaLevel> list= iqcOqaLevelService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/iqcOqaLevel/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        IqcOqaLevel model = iqcOqaLevelService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/iqcOqaLevel/save")
    public Result save(@RequestBody IqcOqaLevel iqcOqaLevel) {
        String depaName = umsDepaService.getDepaNameByCode(iqcOqaLevel.getDepaCode());
        iqcOqaLevel.setDepaName(depaName);
        iqcOqaLevelService.saveOrUpdate(iqcOqaLevel);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/iqcOqaLevel/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        iqcOqaLevelService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/iqcOqaLevel/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<IqcOqaLevel> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, IqcOqaLevel.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        iqcOqaLevelService.save(u);
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
    @PostMapping("/iqcOqaLevel/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<IqcOqaLevel> iqcOqaLevels =new ArrayList<>();
        List<IqcOqaLevel> iqcOqaLevelList = iqcOqaLevelService.list(new QueryWrapper<IqcOqaLevel>().eq("cu_id", cuId));
        if (iqcOqaLevelList.isEmpty()) {iqcOqaLevels.add(iqcOqaLevelService.getById(0)); } else {
            for (IqcOqaLevel iqcOqaLevel : iqcOqaLevelList) {
                iqcOqaLevels.add(iqcOqaLevel);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(iqcOqaLevels, "检验水准导出", "检验水准导出", IqcOqaLevel.class, "iqcOqaLevel.xls", response);

    }
}
