package com.rh.i_mes_plus.vo;

import lombok.Data;

@Data
public class UmsPermissionVO {

    /** 主键 */
    private Long id;
    /** 权限名 */
    private String title;
    /** 权限路径 */
    private String href;
    /** 权限类型 例如 0 菜单 2按钮 */
    private Integer perType;
    /** 权限标识码 */
    private String perCode;
    /** 图标 */
    private String icon;
    /** 一级权限 默认 0  */
    private Integer parentId;
    /** 状态 0 禁用 1 启用 */
    private Integer status;
    /** 描述 */
    private String remark;

}
