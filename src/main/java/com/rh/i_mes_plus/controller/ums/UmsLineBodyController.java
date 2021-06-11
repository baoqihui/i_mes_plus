package com.rh.i_mes_plus.controller.ums;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.ums.UmsLineBody;
import com.rh.i_mes_plus.service.ums.IUmsLineBodyService;
import com.rh.i_mes_plus.util.EasyPoiExcelUtil;
import com.rh.i_mes_plus.vo.ChildVO;
import com.rh.i_mes_plus.vo.OneVO;
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
 * 线别管理
 *
 * @author hbq
 * @date 2021-05-20 15:31:14
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "线别管理")
@RequestMapping("ums")
public class UmsLineBodyController {
    @Autowired
    private IUmsLineBodyService umsLineBodyService;

    @ApiOperation(value = "(三级)线别管理树形列表")
    @PostMapping("/umsLineBody/treeList")
    public Result treeList(@RequestBody Map<String, Object> params) {
        List<OneVO> oneVOS=umsLineBodyService.treeList(params);
        return Result.succeed(oneVOS,"查询成功");
    }

    @ApiOperation(value = "递归查询树状线别信息")
    @PostMapping("/umsLineBody/allTree")
    public Result selectAllTree(Map<String,List<Long>> map) {
        List<ChildVO> childVOS = umsLineBodyService.selectAllTree("0");
        return Result.succeed(childVOS, "查询成功");
    }
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/umsLineBody/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= umsLineBodyService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/umsLineBody/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        UmsLineBody model = umsLineBodyService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/umsLineBody/save")
    public Result save(@RequestBody UmsLineBody umsLineBody) {
        Long id = umsLineBody.getId();
        if (id!=null){
            UmsLineBody existing = umsLineBodyService.getById(id);
            //编辑考虑下级
            umsLineBodyService.update(new LambdaUpdateWrapper<UmsLineBody>()
                    .eq(UmsLineBody::getParentLineCode,existing.getLineCode())
                    .set(UmsLineBody::getParentLineCode,umsLineBody.getLineCode())
            );
        }
        umsLineBodyService.saveOrUpdate(umsLineBody);
        return Result.succeed("保存成功");
    }
    /**
     * 批量新增or更新
     */
    @ApiOperation(value = "批量新增or更新")
    @PostMapping("/umsLineBody/saveBatch")
    public Result saveBatch(@RequestBody Map<String,List<UmsLineBody>> map) {
        List<UmsLineBody> models = map.get("models");
        umsLineBodyService.saveOrUpdateBatch(models);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/umsLineBody/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        umsLineBodyService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/umsLineBody/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<UmsLineBody> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, UmsLineBody.class);
            rowNum = list.size();
            if (rowNum > 0) {
                list.forEach(u -> {
                        umsLineBodyService.save(u);
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
    @PostMapping("/umsLineBody/leadOut")
    public void leadOut(@RequestBody Map<String,List<Long>> map,HttpServletResponse response) throws IOException {
        List<Long> ids = map.get("ids");
        List<UmsLineBody> umsLineBodyList = ids==null||ids.isEmpty()? umsLineBodyService.list():(List)umsLineBodyService.listByIds(ids);
        //导出操作
        EasyPoiExcelUtil.exportExcel(umsLineBodyList, "线别管理导出", "线别管理导出", UmsLineBody.class, "umsLineBody.xls", response);
    }
}
