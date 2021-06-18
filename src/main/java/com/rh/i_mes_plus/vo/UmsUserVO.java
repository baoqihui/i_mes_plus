package com.rh.i_mes_plus.vo;

import lombok.Data;

import java.util.List;


/**
 * 用户信息展示
 * @author lsw
 * @date 2019-12-10
 */
@Data
public class UmsUserVO {

    /** 用户编号 */
    private Integer id;
    /** 用户名 */
    private String userName;
    /** 账号 */
    private String userAccount;
    /** token */
    private String token;
    /** 用户权限 */
    private List<UmsPermissionVO> umsPermissionVOS;


}
