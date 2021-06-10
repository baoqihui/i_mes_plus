package com.rh.i_mes_plus.model.ums;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户表
 *
 * @author hqb
 * @date 2020-09-23 15:44:24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_user")
public class UmsUser extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "账号（编号）")
        @ApiModelProperty(value = "账号（编号）")
        private String userAccount;
        @Excel(name = "用户名")
        @ApiModelProperty(value = "用户名")
        private String userName;
        @Excel(name = "密码")
        @ApiModelProperty(value = "密码")
        private String userPwd;
        @Excel(name = "邮箱")
        @ApiModelProperty(value = "邮箱")
        private String email;
        @Excel(name = "是否外部员工")
        @ApiModelProperty(value = "是否外部员工")
        private Boolean isExter;
        @ApiModelProperty(value = "状态 0 禁用 1 启用")
        private Boolean state;
        @Excel(name = "部门代码")
        @ApiModelProperty(value = "部门代码")
        private String depaCode;
        @Excel(name = "部门名称")
        @ApiModelProperty(value = "部门名称")
        private String depaName;
}
