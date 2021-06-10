package com.rh.i_mes_plus.model.ums;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户权限关联表
 *
 * @author hqb
 * @date 2020-09-12 16:31:21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_role_per")
public class UmsRolePer extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "权限ID")
        @ApiModelProperty(value = "权限ID")
        private Long perId;
        @Excel(name = "角色ID")
        @ApiModelProperty(value = "角色ID")
        private Long roleId;
}
