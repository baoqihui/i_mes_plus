package com.rh.i_mes_plus.controller.ums;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.ums.UmsFeederType;
import com.rh.i_mes_plus.service.ums.IUmsFeederTypeService;
import com.rh.i_mes_plus.util.EasyPoiExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 料枪类型
 *
 * @author hbq
 * @date 2021-05-21 15:02:34
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "料枪类型")
@RequestMapping("ums")
public class UmsFeederTypeController {
    @Autowired
    private IUmsFeederTypeService umsFeederTypeService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/umsFeederType/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= umsFeederTypeService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/umsFeederType/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        UmsFeederType model = umsFeederTypeService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/umsFeederType/save")
    public Result save(@RequestBody UmsFeederType umsFeederType) {
        umsFeederTypeService.saveOrUpdate(umsFeederType);
        return Result.succeed("保存成功");
    }

    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/umsFeederType/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<UmsFeederType>> map) {
        List<UmsFeederType> models = map.get("models");
        umsFeederTypeService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/umsFeederType/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        umsFeederTypeService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/umsFeederType/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<UmsFeederType> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, UmsFeederType.class);
            rowNum = list.size();
            if (rowNum > 0) {
                list.forEach(u -> {
                        umsFeederTypeService.save(u);
                });
                return Result.succeed("成功导入信息"+rowNum+"行数据");
            }
        }
        return Result.failed("导入失败");
    }
    
    /**
     * 导出（传入ids数组，选择指定id）
     */
    @ApiOperation(value = "导出（传入ids数组，选择指定id）")
    @PostMapping("/umsFeederType/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<UmsFeederType> umsFeederTypeList = ids==null||ids.isEmpty()? umsFeederTypeService.list():(List)umsFeederTypeService.listByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(umsFeederTypeList, "料枪类型导出", "料枪类型导出", UmsFeederType.class, "umsFeederType.xls", response);
    }
}
