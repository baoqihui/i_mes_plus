package com.rh.i_mes_plus.model.ums;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 客户管理表
 *
 * @author hqb
 * @date 2020-09-23 15:03:46
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_customer")
public class UmsCustomer extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "客户名称")
        @ApiModelProperty(value = "客户名称")
        private String customer;
        @Excel(name = "")
        @ApiModelProperty(value = "")
        private String passwd;
        @Excel(name = "客户编号")
        @ApiModelProperty(value = "客户编号")
        private String custCode;
        @Excel(name = "")
        @ApiModelProperty(value = "")
        private String custCode2;
        @Excel(name = "")
        @ApiModelProperty(value = "")
        private String primaryCode;
        @Excel(name = "抽检批号")
        @ApiModelProperty(value = "抽检批号")
        private String oqaCode;
        @Excel(name = "客户等级")
        @ApiModelProperty(value = "客户等级")
        private String customerGrade;
        @Excel(name = "是否管控客户标识码(Y/N)默认N")
        @ApiModelProperty(value = "是否管控客户标识码(Y/N)默认N")
        private String ctCtlCodeFlag;
        @Excel(name = "物料共用(Y/N)默认N")
        @ApiModelProperty(value = "物料共用(Y/N)默认N")
        private String ctPublic;
        @Excel(name = "定额损耗规则0—物料类型1—物料(默认0)")
        @ApiModelProperty(value = "定额损耗规则0—物料类型1—物料(默认0)")
        private String ctLossRules;
        @Excel(name = "物料条码规则")
        @ApiModelProperty(value = "物料条码规则")
        private String tbrId;
        @Excel(name = "是否校验客户标识码(Y/N)默认为N")
        @ApiModelProperty(value = "是否校验客户标识码(Y/N)默认为N")
        private String ctChkCodeFlag;
        @Excel(name = "客户简称")
        @ApiModelProperty(value = "客户简称")
        private String custAbbreviation;
        @Excel(name = "标签简码")
        @ApiModelProperty(value = "标签简码")
        private String labelAbbreviation;
        @Excel(name = "启用OQC(Y/N)")
        @ApiModelProperty(value = "启用OQC(Y/N)")
        private String ctOqcEnable;
        @Excel(name = "铭牌超期时间（天）")
        @ApiModelProperty(value = "铭牌超期时间（天）")
        private Long ctNameplate;
        @Excel(name = "编辑日期",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "编辑日期")
        private Date editdate;
        @Excel(name = "客户地址")
        @ApiModelProperty(value = "客户地址")
        private String customerAddress;
        @Excel(name = "项目号")
        @ApiModelProperty(value = "项目号")
        private String projectCode;
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
