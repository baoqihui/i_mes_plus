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
 * 条码规则详情
 *
 * @author hbq
 * @date 2021-06-28 18:41:07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_barcode_rule_detail")
public class SmsBarcodeRuleDetail extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "规则代码")
        @ApiModelProperty(value = "规则代码")
        private String ruleCode;
        @Excel(name = "规则名称")
        @ApiModelProperty(value = "规则名称")
        private String ruleName;
        @Excel(name = "字段名（工单号/机型代码/批次号/版本/供应商/时间）")
        @ApiModelProperty(value = "字段名（工单号/机型代码/批次号/版本/供应商/时间）")
        private String fieldName;
        @Excel(name = "连接符")
        @ApiModelProperty(value = "连接符")
        private String jointMark;
        @Excel(name = "流水号长度")
        @ApiModelProperty(value = "流水号长度")
        private Integer fieldLimit;
        @Excel(name = "排序")
        @ApiModelProperty(value = "排序")
        private Long sort;
}
