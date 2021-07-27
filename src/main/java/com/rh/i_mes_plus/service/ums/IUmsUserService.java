package com.rh.i_mes_plus.service.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.UserRoleDTO;
import com.rh.i_mes_plus.model.ums.UmsUser;
import com.rh.i_mes_plus.vo.UmsUserVO;

import java.util.List;
import java.util.Map;

/**
 * 用户表
 *
 * @author hqb
 * @date 2020-09-12 16:31:21
 */
public interface IUmsUserService extends IService<UmsUser> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<UmsUser> findList(Map<String, Object> params);

    Map<String, Object> getUser(String username, String password);

    Result authLogin(Map<String, Object> map);

    Result getInfo();

    Result logout();

    Result saveOrUpdateUser(UserRoleDTO userRoleDTO);

    List<UmsUser> getManager(Map<String, Object> params);

    Result<UmsUserVO> pdaLogin(UmsUser umsUser);

    String mobileLogin(Map<String, Object> map);
}

