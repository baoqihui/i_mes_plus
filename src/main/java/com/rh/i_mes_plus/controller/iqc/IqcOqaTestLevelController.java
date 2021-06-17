package com.rh.i_mes_plus.controller.iqc;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.iqc.IqcOqaTestLevel;
import com.rh.i_mes_plus.service.iqc.IIqcOqaTestLevelService;
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
 * 物料抽样方案
 *
 * @author hqb
 * @date 2020-10-16 11:39:52
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "物料抽样方案")
@RequestMapping("iqc")
public class IqcOqaTestLevelController {
    @Autowired
    private IIqcOqaTestLevelService iqcOqaTestLevelService;
    @Autowired
    private IUmsDepaService umsDepaService;

    @ApiOperation(value = "查询列表")
    @PostMapping("/iqcOqaTestLevel/listAll")
    public Result<PageResult> listAll(@RequestBody Map<String, Object> params) {
        String code = MapUtil.getStr(params, "depaCode");
        List<String> umsDepas = StrUtil.isNotEmpty(code)?umsDepaService.getSon(params):new ArrayList<>();
        params.put("umsDepas",umsDepas);
        Page<Map> list= iqcOqaTestLevelService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    /**
     * 查询
     */
    @ApiOperation(value = "修改时查询")
    @PostMapping("/iqcOqaTestLevel/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        IqcOqaTestLevel model = iqcOqaTestLevelService.getById(id);
        String itemCode = model.getItemCode();
        String otsCode = model.getOtsCode();
        Boolean isDef = model.getIsDef();
        String depaCode = model.getDepaCode();
        List<IqcOqaTestLevel> list = iqcOqaTestLevelService.list(new QueryWrapper<IqcOqaTestLevel>()
                .eq("item_code", itemCode).eq("ots_code", otsCode).eq("is_def",isDef)
                .eq("depa_code",depaCode)
        );
        return Result.succeed(list, "查询成功");
    }

    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/iqcOqaTestLevel/save")
    public Result save(@RequestBody Map<String,List<IqcOqaTestLevel>> map) {
        List<IqcOqaTestLevel> modes = map.get("models");
        IqcOqaTestLevel model = iqcOqaTestLevelService.getById(modes.get(0).getId());
        if (model!=null){
            String itemCode = model.getItemCode();
            String otsCode = model.getOtsCode();
            Boolean isDef = model.getIsDef();
            String depaCode = model.getDepaCode();
            iqcOqaTestLevelService.remove(new QueryWrapper<IqcOqaTestLevel>()
                    .eq("item_code", itemCode).eq("ots_code", otsCode).eq("is_def",isDef)
                    .eq("depa_code",depaCode)
            );
        }
        iqcOqaTestLevelService.saveBatch(modes);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/iqcOqaTestLevel/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        iqcOqaTestLevelService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/iqcOqaTestLevel/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<IqcOqaTestLevel> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, IqcOqaTestLevel.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        iqcOqaTestLevelService.save(u);
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
    @PostMapping("/iqcOqaTestLevel/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<IqcOqaTestLevel> iqcOqaTestLevels =new ArrayList<>();
        List<IqcOqaTestLevel> iqcOqaTestLevelList = iqcOqaTestLevelService.list(new QueryWrapper<IqcOqaTestLevel>().eq("cu_id", cuId));
        if (iqcOqaTestLevelList.isEmpty()) {iqcOqaTestLevels.add(iqcOqaTestLevelService.getById(0)); } else {
            for (IqcOqaTestLevel iqcOqaTestLevel : iqcOqaTestLevelList) {
                iqcOqaTestLevels.add(iqcOqaTestLevel);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(iqcOqaTestLevels, "物料抽样方案导出", "物料抽样方案导出", IqcOqaTestLevel.class, "iqcOqaTestLevel.xls", response);

    }
}
