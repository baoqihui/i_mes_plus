package com.rh.i_mes_plus.controller.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.ums.UmsUserRole;
import com.rh.i_mes_plus.service.ums.IUmsUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户角色关联表
 *
 * @author hbq
 * @date 2020-09-12 16:31:21
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "用户角色关联表")
@RequestMapping("/ums")
public class UmsUserRoleController {
    @Autowired
    private IUmsUserRoleService umsUserRoleService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/umsUserRole/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<UmsUserRole> list= umsUserRoleService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/umsUserRole/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        UmsUserRole model = umsUserRoleService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "保存")
    @PostMapping("/umsUserRole/save")
    public Result save(@RequestBody UmsUserRole umsUserRole) {
        umsUserRoleService.saveOrUpdate(umsUserRole);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @PostMapping("/umsUserRole/del/{id}")
    public Result delete(@PathVariable Long id) {
        umsUserRoleService.removeById(id);
        return Result.succeed("删除成功");
    }
}
