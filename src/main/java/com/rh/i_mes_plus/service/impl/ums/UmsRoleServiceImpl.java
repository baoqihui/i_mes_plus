package com.rh.i_mes_plus.service.impl.ums;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.RoleInsertDTO;
import com.rh.i_mes_plus.mapper.ums.UmsRoleMapper;
import com.rh.i_mes_plus.mapper.ums.UmsRolePerMapper;
import com.rh.i_mes_plus.model.ums.UmsRole;
import com.rh.i_mes_plus.model.ums.UmsRolePer;
import com.rh.i_mes_plus.model.ums.UmsUserRole;
import com.rh.i_mes_plus.service.ums.IUmsRoleService;
import com.rh.i_mes_plus.service.ums.IUmsUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 角色表
 *
 * @author hqb
 * @date 2020-09-12 16:38:04
 */
@Slf4j
@Service
public class UmsRoleServiceImpl extends ServiceImpl<UmsRoleMapper, UmsRole> implements IUmsRoleService {
    @Resource
    private UmsRoleMapper umsRoleMapper;
    @Resource
    private UmsRolePerMapper umsRolePerMapper;
    @Autowired
    private IUmsUserRoleService umsUserRoleService;
    /**
     * 列表
     * @param params
     * @return
     */
    public Page<Map> findList(Map<String, Object> params){
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<UmsRole> pages = new Page<>(pageNum, pageSize);
        return umsRoleMapper.findList(pages, params);
    }
    @Override
    public Result insertRole(RoleInsertDTO roleInsertDTO) {
        UmsRole umsRole=new UmsRole();
        BeanUtil.copyProperties(roleInsertDTO,umsRole);
        umsRoleMapper.insert(umsRole);
        Long roleId = umsRole.getId();
        List<Long> perIdList = roleInsertDTO.getPerIdList();
        UmsRolePer umsRolePer=new UmsRolePer();
        umsRolePer.setRoleId(roleId);
        for (Long perId : perIdList) {
            umsRolePer.setPerId(perId);
            umsRolePerMapper.insert(umsRolePer);
        }
        return Result.succeed("添加成功");
    }

    @Override
    public Result editRole(RoleInsertDTO roleInsertDTO) {
        UmsRole umsRole=new UmsRole();
        BeanUtil.copyProperties(roleInsertDTO,umsRole);
        umsRoleMapper.updateById(umsRole);
        Long roleId = umsRole.getId();
        umsRolePerMapper.delete(new QueryWrapper<UmsRolePer>().eq("role_id",roleId));
        List<Long> perIdList = roleInsertDTO.getPerIdList();
        UmsRolePer umsRolePer=new UmsRolePer();
        umsRolePer.setRoleId(roleId);
        for (Long perId : perIdList) {
            umsRolePer.setPerId(perId);
            umsRolePerMapper.insert(umsRolePer);
        }
        return Result.succeed("修改成功");
    }

    @Override
    public List<UmsRole>  getRoleListByUid(Long id) {
        return umsRoleMapper.getRoleListByUid(id);
    }

    @Override
    public Result delete(Long id) {
        List<UmsUserRole> userRoles = umsUserRoleService.list(new LambdaQueryWrapper<UmsUserRole>()
                .eq(UmsUserRole::getRoleId, id)
        );
        if (!userRoles.isEmpty()) {
            return Result.failed("请先解绑该角色绑定的用户");
        }
        removeById(id);
        umsRolePerMapper.delete(new LambdaQueryWrapper<UmsRolePer>().eq(UmsRolePer::getRoleId,id));
        return Result.succeed("删除成功");
    }
}
