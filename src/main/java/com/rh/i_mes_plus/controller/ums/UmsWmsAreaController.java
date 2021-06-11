package com.rh.i_mes_plus.controller.ums;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.common.model.SysConst;
import com.rh.i_mes_plus.dto.UmsWmsAreaDTO;
import com.rh.i_mes_plus.model.ums.UmsWmsArea;
import com.rh.i_mes_plus.service.ums.IUmsWmsAreaService;
import com.rh.i_mes_plus.service.ums.IUmsDepaService;
import com.rh.i_mes_plus.util.EasyPoiExcelUtil;
import com.rh.i_mes_plus.vo.OneVO;
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
 * 
 *
 * @author hqb
 * @date 2020-09-24 13:06:30
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "仓储信息")
@RequestMapping("ums")
public class UmsWmsAreaController {
    @Autowired
    private IUmsWmsAreaService umsWmsAreaService;
    @Autowired
    private IUmsDepaService umsDepaService;
    @ApiOperation(value = "(三级)辅料储位树形列表")
    @PostMapping("/umsWmsArea/treeList")
    public Result treeList(@RequestBody Map<String, Object> params) {
        String code = MapUtil.getStr(params, "depaCode");
        List<String> umsDepas = StrUtil.isNotEmpty(code)?umsDepaService.getSon(params):new ArrayList<>();
        params.put("umsDepas",umsDepas);
        List<OneVO> oneVOS=umsWmsAreaService.treeList(params);
        return Result.succeed(oneVOS,"查询成功");
    }
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/umsWmsArea/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<UmsWmsArea> list= umsWmsAreaService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }
    /**
     * 查询
     */
    @ApiOperation(value = "查询上级")
    @PostMapping("/umsWmsArea/selParent/{id}")
    public Result findParentById(@PathVariable Long id) {
        UmsWmsArea model = umsWmsAreaService.getById(id);
        String fatherSn = model.getAtCode();
        List<UmsWmsArea> list =new ArrayList<>();
        if (SysConst.WMS_AT_TYPE.PAT.equals(fatherSn)){
            list=  umsWmsAreaService.list(new QueryWrapper<UmsWmsArea>().eq("at_code", SysConst.WMS_AT_TYPE.AAT));
        }else if (SysConst.WMS_AT_TYPE.AAT.equals(fatherSn)){
           list= umsWmsAreaService.list(new QueryWrapper<UmsWmsArea>().eq("at_code", SysConst.WMS_AT_TYPE.SAT));
        }
        return Result.succeed(list, "查询成功");
    }
    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/umsWmsArea/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        UmsWmsArea model = umsWmsAreaService.getById(id);
        return Result.succeed(model, "查询成功");
    }
    @ApiOperation(value = "查询S级仓库")
    @PostMapping("/umsWmsArea/selSarea")
    public Result selSarea() {
        List<UmsWmsArea> list = umsWmsAreaService.list(new QueryWrapper<UmsWmsArea>().eq("AT_CODE", "S"));
        return Result.succeed(list, "查询成功");
    }
    @ApiOperation(value = "批量生成库位")
    @PostMapping("/umsWmsArea/autoGenerate")
    public Result autoGenerate(@RequestBody UmsWmsAreaDTO umsWmsAreaDTO) {
        return umsWmsAreaService.autoGenerate(umsWmsAreaDTO);
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/umsWmsArea/save")
    public Result save(@RequestBody UmsWmsArea umsWmsArea) {
        umsWmsAreaService.saveOrUpdate(umsWmsArea);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/umsWmsArea/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        umsWmsAreaService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/umsWmsArea/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        return umsWmsAreaService.leadIn(excel);
    }
    
    /**
     * 导出
     */
    @ApiOperation(value = "导出")
    @PostMapping("/umsWmsArea/leadOut")
    public void leadOut( HttpServletResponse response) throws IOException {
        List<UmsWmsArea> umsWmsAreas =new ArrayList<>();
        List<UmsWmsArea> umsWmsAreaList = umsWmsAreaService.list(new QueryWrapper<>());
        if (umsWmsAreaList.isEmpty()) {umsWmsAreas.add(umsWmsAreaService.getById(0)); } else {
            for (UmsWmsArea umsWmsArea : umsWmsAreaList) {
                umsWmsAreas.add(umsWmsArea);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(umsWmsAreas, "仓储信息导出", "仓储信息导出", UmsWmsArea.class, "umsWmsArea.xls", response);

    }
}
