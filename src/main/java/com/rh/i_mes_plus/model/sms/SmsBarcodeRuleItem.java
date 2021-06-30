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
 * 物料条码规则
 *
 * @author hbq
 * @date 2021-06-28 18:41:07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_barcode_rule_item")
public class SmsBarcodeRuleItem extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "机种代码")
        @ApiModelProperty(value = "机种代码")
        private String itemCode;
        @Excel(name = "机种名称")
        @ApiModelProperty(value = "机种名称")
        private String itemName;
        @Excel(name = "客户条码规则")
        @ApiModelProperty(value = "客户条码规则")
        private String ruleCode;
}
