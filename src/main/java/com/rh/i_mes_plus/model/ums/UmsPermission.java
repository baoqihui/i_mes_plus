package com.rh.i_mes_plus.model.ums;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限表
 *
 * @author hbq
 * @date 2021-03-30 16:46:48
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_permission")
public class UmsPermission extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "权限名")
        @ApiModelProperty(value = "权限名")
        private String perName;
        @Excel(name = "权限路径")
        @ApiModelProperty(value = "权限路径")
        private String path;
        @Excel(name = "组件路径")
        @ApiModelProperty(value = "组件路径")
        private String component;
        @Excel(name = "图标")
        @ApiModelProperty(value = "图标")
        private String icon;
        @Excel(name = "排序")
        @ApiModelProperty(value = "排序")
        private Integer perSort;
        @Excel(name = "权限类型 例如 0 菜单 1按钮")
        @ApiModelProperty(value = "权限类型 例如 0 菜单 1按钮")
        private Integer perType;
        @Excel(name = "上级权限 默认 0 ")
        @ApiModelProperty(value = "上级权限 默认 0 ")
        private Long parentId;
        @Excel(name = "状态 0 禁用 1 启用",replace ={"否_false","是_true"})
        @ApiModelProperty(value = "状态 0 禁用 1 启用")
        private Boolean status;
        @Excel(name = "是否缓存")
        @ApiModelProperty(value = "是否缓存")
        private Integer keepAlive;
        @Excel(name = "描述")
        @ApiModelProperty(value = "描述")
        private String remark;
}
