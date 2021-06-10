package com.rh.i_mes_plus.controller.ums;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.ums.UmsAcceArea;
import com.rh.i_mes_plus.service.ums.IUmsAcceAreaService;
import com.rh.i_mes_plus.util.EasyPoiExcelUtil;
import com.rh.i_mes_plus.vo.ChildVO;
import com.rh.i_mes_plus.vo.UmsAcceAreaVO;
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
 * 辅料储位
 *
 * @author hqb
 * @date 2020-09-22 13:34:31
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "辅料储位")
@RequestMapping("ums")
public class UmsAcceAreaController {
    @Autowired
    private IUmsAcceAreaService umsAcceAreaService;

    @ApiOperation(value = "（递归）查询全部树状权限列表")
    @PostMapping("/umsAcceArea/allTree")
    public Result selectAllTree(Map<String,List<Long>> map) {
        List<ChildVO> childVOS = umsAcceAreaService.selectAllTree("0");
        return Result.succeed(childVOS, "查询成功");
    }
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/umsAcceArea/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<UmsAcceArea> list= umsAcceAreaService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/umsAcceArea/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        UmsAcceArea model = umsAcceAreaService.getById(id);
        UmsAcceAreaVO umsAcceAreaVO = new UmsAcceAreaVO();
        BeanUtil.copyProperties(model,umsAcceAreaVO);
        if (model != null) {
            String fatherSn = model.getArFatherSn();
            UmsAcceArea ar_sn = umsAcceAreaService.getOne(new QueryWrapper<UmsAcceArea>().eq("ar_sn", fatherSn));
            if (ar_sn != null) {
                String arName = ar_sn.getArName();
                umsAcceAreaVO.setArFatherName(arName);
            }
        }
        return Result.succeed(umsAcceAreaVO, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/umsAcceArea/save")
    public Result save(@RequestBody UmsAcceArea umsAcceArea) {
        umsAcceAreaService.saveOrUpdate(umsAcceArea);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/umsAcceArea/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        umsAcceAreaService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/umsAcceArea/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<UmsAcceArea> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, UmsAcceArea.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        umsAcceAreaService.save(u);
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
    @PostMapping("/umsAcceArea/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<UmsAcceArea> umsAcceAreas =new ArrayList<>();
        List<UmsAcceArea> umsAcceAreaList = umsAcceAreaService.list(new QueryWrapper<UmsAcceArea>().eq("cu_id", cuId));
        if (umsAcceAreaList.isEmpty()) {umsAcceAreas.add(umsAcceAreaService.getById(0)); } else {
            for (UmsAcceArea umsAcceArea : umsAcceAreaList) {
                umsAcceAreas.add(umsAcceArea);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(umsAcceAreas, "辅料储位导出", "辅料储位导出", UmsAcceArea.class, "umsAcceArea.xls", response);

    }
}
