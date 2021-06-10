package com.rh.i_mes_plus.controller.ums;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.ums.UmsCustomerGrade;
import com.rh.i_mes_plus.service.ums.IUmsCustomerGradeService;
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
 * 客户等级（暂时不用）
 *
 * @author hqb
 * @date 2020-09-21 08:41:57
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "客户等级")
@RequestMapping("ums")
public class UmsCustomerGradeController {
    @Autowired
    private IUmsCustomerGradeService umsCustomerGradeService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/umsCustomerGrade/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<UmsCustomerGrade> list= umsCustomerGradeService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/umsCustomerGrade/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        UmsCustomerGrade model = umsCustomerGradeService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/umsCustomerGrade/save")
    public Result save(@RequestBody UmsCustomerGrade umsCustomerGrade) {
        umsCustomerGradeService.saveOrUpdate(umsCustomerGrade);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/umsCustomerGrade/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        umsCustomerGradeService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/umsCustomerGrade/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<UmsCustomerGrade> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, UmsCustomerGrade.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        umsCustomerGradeService.save(u);
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
    @PostMapping("/umsCustomerGrade/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<UmsCustomerGrade> umsCustomerGrades =new ArrayList<>();
        List<UmsCustomerGrade> umsCustomerGradeList = umsCustomerGradeService.list(new QueryWrapper<UmsCustomerGrade>().eq("cu_id", cuId));
        if (umsCustomerGradeList.isEmpty()) {umsCustomerGrades.add(umsCustomerGradeService.getById(0)); } else {
            for (UmsCustomerGrade umsCustomerGrade : umsCustomerGradeList) {
                umsCustomerGrades.add(umsCustomerGrade);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(umsCustomerGrades, "客户等级导出", "客户等级导出", UmsCustomerGrade.class, "umsCustomerGrade.xls", response);

    }
}
