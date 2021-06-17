package com.rh.i_mes_plus.controller.iqc;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.iqc.IqcCoErrorCode;
import com.rh.i_mes_plus.service.iqc.IIqcCoErrorCodeService;
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
 * 错误代码
 *
 * @author hbq
 * @date 2020-10-23 08:51:08
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "错误代码")
@RequestMapping("iqc")
public class IqcCoErrorCodeController {
    @Autowired
    private IIqcCoErrorCodeService iqcCoErrorCodeService;


    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/iqcCoErrorCode/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= iqcCoErrorCodeService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/iqcCoErrorCode/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        IqcCoErrorCode model = iqcCoErrorCodeService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/iqcCoErrorCode/save")
    public Result save(@RequestBody IqcCoErrorCode iqcCoErrorCode) {
        iqcCoErrorCodeService.saveOrUpdate(iqcCoErrorCode);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/iqcCoErrorCode/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<IqcCoErrorCode>> map) {
        List<IqcCoErrorCode> models = map.get("models");
        iqcCoErrorCodeService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/iqcCoErrorCode/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        iqcCoErrorCodeService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/iqcCoErrorCode/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<IqcCoErrorCode> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, IqcCoErrorCode.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        iqcCoErrorCodeService.save(u);
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
    @PostMapping("/iqcCoErrorCode/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<IqcCoErrorCode> iqcCoErrorCodes =new ArrayList<>();
        List<IqcCoErrorCode> iqcCoErrorCodeList = iqcCoErrorCodeService.list(new QueryWrapper<IqcCoErrorCode>().eq("cu_id", cuId));
        if (iqcCoErrorCodeList.isEmpty()) {iqcCoErrorCodes.add(iqcCoErrorCodeService.getById(0)); } else {
            for (IqcCoErrorCode iqcCoErrorCode : iqcCoErrorCodeList) {
                iqcCoErrorCodes.add(iqcCoErrorCode);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(iqcCoErrorCodes, "错误代码导出", "错误代码导出", IqcCoErrorCode.class, "iqcCoErrorCode.xls", response);

    }
}
