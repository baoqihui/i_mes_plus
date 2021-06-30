package com.rh.i_mes_plus.model.sms;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import com.rh.i_mes_plus.common.model.SuperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 条码规则管理
 *
 * @author hbq
 * @date 2021-06-28 18:41:06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_barcode_rule")
public class SmsBarcodeRule extends SuperEntity {
    private static final long serialVersionUID=1L;

    @Excel(name = "规则代码")
    @ApiModelProperty(value = "规则代码")
    private String ruleCode;
    @Excel(name = "名称")
    @ApiModelProperty(value = "名称")
    private String ruleName;
    @Excel(name = "描述")
    @ApiModelProperty(value = "描述")
    private String ruleDesc;
    @Excel(name = "条码类型")
    @ApiModelProperty(value = "条码类型")
    private String type;
    @Excel(name = "客户代码")
    @ApiModelProperty(value = "客户代码")
    private String custCode;
    @Excel(name = "是否长度校验")
    @ApiModelProperty(value = "是否长度校验")
    private Integer isCheckLimit;
    @Excel(name = "条码长度")
    @ApiModelProperty(value = "条码长度")
    private Integer barcodeLimit;
    @Excel(name = "默认标识")
    @ApiModelProperty(value = "默认标识")
    private Integer isDefault;
    @Excel(name = "连接符")
    @ApiModelProperty(value = "连接符")
    private String jointMark;
    @Excel(name = "流水号长度")
    @ApiModelProperty(value = "流水号长度")
    private Integer fieldLimit;
}
