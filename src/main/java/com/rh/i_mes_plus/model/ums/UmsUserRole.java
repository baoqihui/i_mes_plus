package com.rh.i_mes_plus.model.ums;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户角色关联表
 *
 * @author hqb
 * @date 2020-09-12 16:31:21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_user_role")
public class UmsUserRole extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "用户ID")
        @ApiModelProperty(value = "用户ID")
        private Long userId;
        @Excel(name = "角色ID")
        @ApiModelProperty(value = "角色ID")
        private Long roleId;
}
