package com.rh.i_mes_plus.controller.iqc;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.iqc.IqcOqaCheckBasis;
import com.rh.i_mes_plus.service.iqc.IIqcOqaCheckBasisService;
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
 * OQA检验依据
 *
 * @author hqb
 * @date 2020-10-16 11:39:51
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "OQA检验依据")
@RequestMapping("iqc")
public class IqcOqaCheckBasisController {
    @Autowired
    private IIqcOqaCheckBasisService iqcOqaCheckBasisService;
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/iqcOqaCheckBasis/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<IqcOqaCheckBasis> list= iqcOqaCheckBasisService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/iqcOqaCheckBasis/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        IqcOqaCheckBasis model = iqcOqaCheckBasisService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/iqcOqaCheckBasis/save")
    public Result save(@RequestBody IqcOqaCheckBasis iqcOqaCheckBasis) {
        iqcOqaCheckBasisService.saveOrUpdate(iqcOqaCheckBasis);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/iqcOqaCheckBasis/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        iqcOqaCheckBasisService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/iqcOqaCheckBasis/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<IqcOqaCheckBasis> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, IqcOqaCheckBasis.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        iqcOqaCheckBasisService.save(u);
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
    @PostMapping("/iqcOqaCheckBasis/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<IqcOqaCheckBasis> iqcOqaCheckBasiss =new ArrayList<>();
        List<IqcOqaCheckBasis> iqcOqaCheckBasisList = iqcOqaCheckBasisService.list(new QueryWrapper<IqcOqaCheckBasis>().eq("cu_id", cuId));
        if (iqcOqaCheckBasisList.isEmpty()) {iqcOqaCheckBasiss.add(iqcOqaCheckBasisService.getById(0)); } else {
            for (IqcOqaCheckBasis iqcOqaCheckBasis : iqcOqaCheckBasisList) {
                iqcOqaCheckBasiss.add(iqcOqaCheckBasis);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(iqcOqaCheckBasiss, "OQA检验依据导出", "OQA检验依据导出", IqcOqaCheckBasis.class, "iqcOqaCheckBasis.xls", response);

    }
}
