package com.rh.i_mes_plus.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoleInsertDTO  {

    /** 主键*/
    private Integer id;
    /** 角色名称 */
    private String roleName;
    /** 启用状态 */
    private Integer status;
    /** 顺序 */
    private Integer sort;
    /** 角色权限关系 */
    private List<Long> perIdList;
}
