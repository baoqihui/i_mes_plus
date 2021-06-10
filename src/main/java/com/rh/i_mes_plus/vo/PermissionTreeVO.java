package com.rh.i_mes_plus.vo;


import lombok.Data;

import java.util.List;

/**
 * 所有权限展示
 * @author hbq
 * @date 2019-12-6
 */
@Data
public class PermissionTreeVO {
    /** 主键 */
    private Long id;
    /** 权限名 */
    private String perName;
    /** 权限路径 */
    private String path;
    /** 组件路径 */
    private String component;
    /** 权限类型 例如 0 菜单 1按钮 */
    private Integer perType;
    /** 父级权限 默认 0  */
    private Long parentId;
    /** 图标 */
    private String icon;
    /** 状态 0 禁用 1 启用 */
    private Boolean status;
    /** 描述 */
    private String remark;
    /** 排序 */
    private Integer perSort;
    /** 子权限 */
    private List<PermissionTreeVO> children;
}
