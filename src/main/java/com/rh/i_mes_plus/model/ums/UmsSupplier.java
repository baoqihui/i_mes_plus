package com.rh.i_mes_plus.model.ums;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * BOM料件供应商表
 *
 * @author hqb
 * @date 2020-09-23 13:36:14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_supplier")
public class UmsSupplier extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "供应商名称")
        @ApiModelProperty(value = "供应商名称")
        private String supplierName;
        @Excel(name = "供应商编号")
        @ApiModelProperty(value = "供应商编号")
        private String supplierCode;
        @Excel(name = "供应商简称")
        @ApiModelProperty(value = "供应商简称")
        private String supplierShort;
        @Excel(name = "供应商登录密码(WEB打印)")
        @ApiModelProperty(value = "供应商登录密码(WEB打印)")
        private String supplierPwd;
        @Excel(name = "BOM物料ID")
        @ApiModelProperty(value = "BOM物料ID")
        private Long bomId;
        @TableLogic
        @ApiModelProperty(value = "",hidden = true)
        private Boolean isDel;
        @Excel(name = "部门代码")
        @ApiModelProperty(value = "部门代码")
        private String depaCode;
        @Excel(name = "部门名称")
        @ApiModelProperty(value = "部门名称")
        private String depaName;
}
