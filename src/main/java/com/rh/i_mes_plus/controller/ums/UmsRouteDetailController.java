package com.rh.i_mes_plus.controller.ums;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.ums.UmsRouteDetail;
import com.rh.i_mes_plus.service.ums.IUmsRouteDetailService;
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
 * 流程控制详情
 *
 * @author hbq
 * @date 2021-06-01 09:33:59
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "流程控制详情")
@RequestMapping("ums")
public class UmsRouteDetailController {
    @Autowired
    private IUmsRouteDetailService umsRouteDetailService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/umsRouteDetail/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= umsRouteDetailService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/umsRouteDetail/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        UmsRouteDetail model = umsRouteDetailService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/umsRouteDetail/save")
    public Result save(@RequestBody UmsRouteDetail umsRouteDetail) {
        umsRouteDetailService.saveOrUpdate(umsRouteDetail);
        return Result.succeed("保存成功");
    }

    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/umsRouteDetail/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<UmsRouteDetail>> map) {
        List<UmsRouteDetail> models = map.get("models");
        umsRouteDetailService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/umsRouteDetail/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        umsRouteDetailService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/umsRouteDetail/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<UmsRouteDetail> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, UmsRouteDetail.class);
            rowNum = list.size();
            if (rowNum > 0) {
                list.forEach(u -> {
                        umsRouteDetailService.save(u);
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
    @PostMapping("/umsRouteDetail/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<UmsRouteDetail> umsRouteDetailList = ids==null||ids.isEmpty()? umsRouteDetailService.list():(List)umsRouteDetailService.listByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(umsRouteDetailList, "流程控制详情导出", "流程控制详情导出", UmsRouteDetail.class, "umsRouteDetail.xls", response);
    }
}
