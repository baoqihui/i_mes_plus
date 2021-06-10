package com.rh.i_mes_plus.controller.gtoa;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.gtoa.FmsBurnFile;
import com.rh.i_mes_plus.service.gtoa.IFmsBurnFileService;
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
 * 烧录文件管理
 *
 * @author hbq
 * @date 2020-12-02 09:14:13
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "烧录文件管理")
@RequestMapping("fms")
public class FmsBurnFileController {
    @Autowired
    private IFmsBurnFileService fmsBurnFileService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/fmsBurnFile/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= fmsBurnFileService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/fmsBurnFile/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        FmsBurnFile model = fmsBurnFileService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/fmsBurnFile/save")
    public Result save(@RequestBody FmsBurnFile fmsBurnFile) {
        fmsBurnFileService.saveOrUpdate(fmsBurnFile);
        return Result.succeed("保存成功");
    }

    /**
         * 批量新增or更新
         */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/fmsBurnFile/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<FmsBurnFile>> map) {
        List<FmsBurnFile> models = map.get("models");
        fmsBurnFileService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/fmsBurnFile/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        fmsBurnFileService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/fmsBurnFile/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<FmsBurnFile> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, FmsBurnFile.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        fmsBurnFileService.save(u);
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
    @PostMapping("/fmsBurnFile/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<FmsBurnFile> fmsBurnFiles =new ArrayList<>();
        List<FmsBurnFile> fmsBurnFileList = fmsBurnFileService.list(new QueryWrapper<FmsBurnFile>().eq("cu_id", cuId));
        if (fmsBurnFileList.isEmpty()) {fmsBurnFiles.add(fmsBurnFileService.getById(0)); } else {
            for (FmsBurnFile fmsBurnFile : fmsBurnFileList) {
                fmsBurnFiles.add(fmsBurnFile);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(fmsBurnFiles, "烧录文件管理导出", "烧录文件管理导出", FmsBurnFile.class, "fmsBurnFile.xls", response);

    }
}
