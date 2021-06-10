package com.rh.i_mes_plus.controller.ums;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.ums.UmsCustomer;
import com.rh.i_mes_plus.service.ums.IUmsCustomerService;
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
 * 客户管理表
 *
 * @author hqb
 * @date 2020-09-21 08:41:57
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "客户管理表")
@RequestMapping("ums")
public class UmsCustomerController {
    @Autowired
    private IUmsCustomerService umsCustomerService;
    @Autowired
    private IUmsDepaService umsDepaService;

    @ApiOperation(value = "sap同步客户数据到mes")
    @PostMapping("/umsCustomer/saveAll")
    public Result saveAll(@RequestBody List<Map<String,Object>> maps) {
        return umsCustomerService.saveAll(maps);
    }

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/umsCustomer/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<UmsCustomer> list= umsCustomerService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/umsCustomer/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        UmsCustomer model = umsCustomerService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/umsCustomer/save")
    public Result save(@RequestBody UmsCustomer umsCustomer) {
        String depaName=umsDepaService.getDepaNameByCode(umsCustomer.getDepaCode());
        umsCustomer.setDepaName(depaName);
        umsCustomerService.saveOrUpdate(umsCustomer);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/umsCustomer/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        umsCustomerService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/umsCustomer/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<UmsCustomer> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, UmsCustomer.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        umsCustomerService.save(u);
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
    @PostMapping("/umsCustomer/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<UmsCustomer> umsCustomers =new ArrayList<>();
        List<UmsCustomer> umsCustomerList = umsCustomerService.list(new QueryWrapper<UmsCustomer>().eq("cu_id", cuId));
        if (umsCustomerList.isEmpty()) {umsCustomers.add(umsCustomerService.getById(0)); } else {
            for (UmsCustomer umsCustomer : umsCustomerList) {
                umsCustomers.add(umsCustomer);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(umsCustomers, "客户管理表导出", "客户管理表导出", UmsCustomer.class, "umsCustomer.xls", response);

    }
}
