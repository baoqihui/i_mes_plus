package com.rh.i_mes_plus.service.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.ums.UmsRolePer;

import java.util.Map;

/**
 * 用户权限关联表
 *
 * @author hqb
 * @date 2020-09-12 16:31:21
 */
public interface IUmsRolePerService extends IService<UmsRolePer> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<UmsRolePer> findList(Map<String, Object> params);
}

