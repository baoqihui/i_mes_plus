package com.rh.i_mes_plus.controller.ums;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.UserRoleDTO;
import com.rh.i_mes_plus.model.ums.UmsRole;
import com.rh.i_mes_plus.model.ums.UmsUser;
import com.rh.i_mes_plus.model.ums.UmsUserRole;
import com.rh.i_mes_plus.service.ums.IUmsDepaService;
import com.rh.i_mes_plus.service.ums.IUmsRoleService;
import com.rh.i_mes_plus.service.ums.IUmsUserRoleService;
import com.rh.i_mes_plus.service.ums.IUmsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户表
 *
 * @author hbq
 * @date 2020-09-12 16:31:21
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "用户表")
@RequestMapping("/ums")
public class UmsUserController {
    @Autowired
    private IUmsUserService umsUserService;
    @Autowired
    private IUmsDepaService umsDepaService;
    @Autowired
    private IUmsRoleService umsRoleService;
    @Autowired
    private IUmsUserRoleService umsUserRoleService;

    /**
     * 登录
     */
    @ApiOperation(value = "登录")
    @PostMapping("/auth")
    public Result authLogin(@RequestBody Map<String, Object> map) {
        return umsUserService.authLogin(map);
    }

    /**
     * 查询当前登录用户的信息
     */
    @ApiOperation(value = "查询当前登录用户的信息")
    @PostMapping("/getInfo")
    public Result getInfo() {
        return umsUserService.getInfo();
    }

    /**
     * 登出
     */
    @ApiOperation(value = "登出")
    @PostMapping("/logout")
    public Result logout() {
        return umsUserService.logout();
    }

    /**
     * 列表
     */
    //@RequiresPermissions({"system:user:list"})
    @ApiOperation(value = "查询列表")
    @PostMapping("/umsUser/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        String code = MapUtil.getStr(params, "depaCode");
        List<String> umsDepas = StrUtil.isNotEmpty(code)?umsDepaService.getSon(params):new ArrayList<>();
        params.put("umsDepas",umsDepas);
        Page<UmsUser> list= umsUserService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "根据用户id查询 角色")
    @PostMapping("/umsUser/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        UmsUser umsUser = umsUserService.getById(id);
        List<UmsRole> roles=umsRoleService.getRoleListByUid(umsUser.getId());
        String depaCode = umsUser.getDepaCode();
        List<String> umsDepas =umsDepaService.getAllDepaBycode(new ArrayList<>(),depaCode);
        Map<String,Object> map=new HashMap<>();
        map.put("roles",roles);
        map.put("umsDepas",umsDepas);
        return Result.succeed(map, "查询成功");
    }
    /**
     * 新增
     */

    //@RequiresPermissions({"system:user:add"})
    @ApiOperation(value = "新增用户或修改账号密码")
    @PostMapping("/umsUser/save")
    public Result save(@RequestBody UserRoleDTO userRoleDTO) {
        return umsUserService.saveOrUpdateUser(userRoleDTO);
    }

    /**
     * 删除
     */
    //@RequiresPermissions({"system:user:del"})
    @ApiOperation(value = "删除")
    @PostMapping("/umsUser/del")
    public Result delete(@RequestBody Map<String,List<Long>> map) {
        List<Long> ids = map.get("ids");
        umsUserService.removeByIds(ids);
        for (Long id : ids) {
            umsUserRoleService.remove(new LambdaQueryWrapper<UmsUserRole>().eq(UmsUserRole::getUserId,id));
        }
        return Result.succeed("删除成功");
    }
    /**
     * 禁用/启用
     */
    //@RequiresPermissions({"system:user:update"})
    @ApiOperation(value = "禁用/启用")
    @PostMapping("/umsUser/update")
    public Result update(@RequestBody UmsUser umsUser) {
        umsUserService.updateById(umsUser);
        return Result.succeed("禁用");
    }
}
