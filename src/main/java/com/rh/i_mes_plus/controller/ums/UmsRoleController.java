package com.rh.i_mes_plus.controller.ums;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.RoleInsertDTO;
import com.rh.i_mes_plus.model.ums.UmsRole;
import com.rh.i_mes_plus.model.ums.UmsRolePer;
import com.rh.i_mes_plus.service.ums.IUmsRolePerService;
import com.rh.i_mes_plus.service.ums.IUmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 角色表
 *
 * @author hbq
 * @date 2020-09-12 16:38:04
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "角色表")
@RequestMapping("/ums")
public class UmsRoleController {
    @Autowired
    private IUmsRoleService umsRoleService;
    @Autowired
    private IUmsRolePerService umsRolePerService;
    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/umsRole/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<Map> list= umsRoleService.findList(params);
        List<Map> records = list.getRecords();
        List<Map> results = new ArrayList<>();
        for (Map record : records) {
            Long roleId = MapUtil.getLong(record, "id");
            List<UmsRolePer> umsRolePers = umsRolePerService.list(new LambdaQueryWrapper<UmsRolePer>()
                    .eq(UmsRolePer::getRoleId, roleId));
            List<Long> perIds=umsRolePers.stream().map(u->u.getPerId()).collect(Collectors.toList());
            record.put("perIds",perIds);
            results.add(record);
        }
        list.setRecords(results);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     *
     *
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/umsRole/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        UmsRole model = umsRoleService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "保存")
    @PostMapping("/umsRole/save")
    public Result save(@RequestBody UmsRole umsRole) {
        umsRoleService.saveOrUpdate(umsRole);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @PostMapping("/umsRole/del/{id}")
    public Result delete(@PathVariable Long id) {
        return umsRoleService.delete(id);
    }

    @ApiOperation(value = "添加角色和权限")
    @PostMapping("/umsRole/addRoleAndPer")
    public Result insertRole(@RequestBody RoleInsertDTO roleInsertDTO){
        return umsRoleService.insertRole(roleInsertDTO);
    }

    @ApiOperation(value = "修改角色和权限")
    @PostMapping("/umsRole/editRoleAndPer")
    public Result editRole(@RequestBody RoleInsertDTO roleInsertDTO){
        return umsRoleService.editRole(roleInsertDTO);
    }

}
