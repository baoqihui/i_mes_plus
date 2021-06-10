package com.rh.i_mes_plus.controller.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.model.PageResult;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.ums.UmsRolePer;
import com.rh.i_mes_plus.service.ums.IUmsRolePerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户权限关联表
 *
 * @author hbq
 * @date 2020-09-12 16:31:21
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "用户权限关联表")
@RequestMapping("/ums")
public class UmsRolePerController {
    @Autowired
    private IUmsRolePerService umsRolePerService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @PostMapping("/umsRolePer/list")
    public Result<PageResult> list(@RequestBody Map<String, Object> params) {
        Page<UmsRolePer> list= umsRolePerService.findList(params);
        return Result.succeed(PageResult.restPage(list),"查询成功");
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @PostMapping("/umsRolePer/sel/{id}")
    public Result findUserById(@PathVariable Long id) {
        UmsRolePer model = umsRolePerService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @PostMapping("/umsRolePer/del/{id}")
    public Result delete(@PathVariable Long id) {
        umsRolePerService.removeById(id);
        return Result.succeed("删除成功");
    }

}
