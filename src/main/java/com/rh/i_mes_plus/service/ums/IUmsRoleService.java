package com.rh.i_mes_plus.service.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.RoleInsertDTO;
import com.rh.i_mes_plus.model.ums.UmsRole;

import java.util.List;
import java.util.Map;

/**
 * 角色表
 *
 * @author hqb
 * @date 2020-09-12 16:31:21
 */
public interface IUmsRoleService extends IService<UmsRole> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Result insertRole(RoleInsertDTO roleInsertDTO);

    Result editRole(RoleInsertDTO roleInsertDTO);

    List<UmsRole>  getRoleListByUid(Long id);

    Result delete(Long id);
}

