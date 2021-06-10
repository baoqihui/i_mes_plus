package com.rh.i_mes_plus.controller.ums;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.ums.UmsCustomerAddress;
import com.rh.i_mes_plus.service.ums.IUmsCustomerAddressService;
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
 * 客户地址
 *
 * @author hqb
 * @date 2020-09-21 08:41:57
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "客户地址")
@RequestMapping("ums")
public class UmsCustomerAddressController {
    @Autowired
    private IUmsCustomerAddressService umsCustomerAddressService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/umsCustomerAddress/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<UmsCustomerAddress> list= umsCustomerAddressService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/umsCustomerAddress/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        UmsCustomerAddress model = umsCustomerAddressService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "新增or更新")
    @PostMapping("/umsCustomerAddress/save")
    public Result save(@RequestBody UmsCustomerAddress umsCustomerAddress) {
        umsCustomerAddressService.saveOrUpdate(umsCustomerAddress);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/umsCustomerAddress/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        umsCustomerAddressService.removeByIds(ids);
        return Result.succeed("删除成功");
    }
    
    /**
     * 导入
     */
    @ApiOperation(value = "导入")
    @PostMapping("/umsCustomerAddress/leadIn")
    public  Result leadIn(MultipartFile excel) throws Exception {
        int rowNum = 0;
        if (!excel.isEmpty()) {
            List<UmsCustomerAddress> list = EasyPoiExcelUtil.importExcel(excel, 1, 1, UmsCustomerAddress.class);
            rowNum = list.size();
            if (rowNum > 0) {
                //无该用户信息
                list.forEach(u -> {
                        umsCustomerAddressService.save(u);
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
    @PostMapping("/umsCustomerAddress/leadOut")
    public void leadOut(String cuId, HttpServletResponse response) throws IOException {
        List<UmsCustomerAddress> umsCustomerAddresss =new ArrayList<>();
        List<UmsCustomerAddress> umsCustomerAddressList = umsCustomerAddressService.list(new QueryWrapper<UmsCustomerAddress>().eq("cu_id", cuId));
        if (umsCustomerAddressList.isEmpty()) {umsCustomerAddresss.add(umsCustomerAddressService.getById(0)); } else {
            for (UmsCustomerAddress umsCustomerAddress : umsCustomerAddressList) {
                umsCustomerAddresss.add(umsCustomerAddress);
            }
        }
        //导出操作
        EasyPoiExcelUtil.exportExcel(umsCustomerAddresss, "客户地址导出", "客户地址导出", UmsCustomerAddress.class, "umsCustomerAddress.xls", response);

    }
}
