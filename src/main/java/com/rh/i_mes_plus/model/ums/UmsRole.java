package com.rh.i_mes_plus.model.ums;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色表
 *
 * @author hqb
 * @date 2020-09-12 16:31:21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_role")
public class UmsRole extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "角色名")
        @ApiModelProperty(value = "角色名")
        private String roleName;
        @Excel(name = "排序")
        @ApiModelProperty(value = "排序")
        private Integer sort;
        @Excel(name = "状态 0 禁用 1 启用")
        @ApiModelProperty(value = "状态 0 禁用 1 启用")
        private Boolean status;
}
