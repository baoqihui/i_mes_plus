package com.rh.i_mes_plus.model.ums;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户等级
 *
 * @author hqb
 * @date 2020-09-23 15:03:47
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_customer_grade")
public class UmsCustomerGrade extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "客户等级")
        @ApiModelProperty(value = "客户等级")
        private String customerGrade;
        @Excel(name = "客户等级名称")
        @ApiModelProperty(value = "客户等级名称")
        private String customerGradeName;
        @Excel(name = "机构代码")
        @ApiModelProperty(value = "机构代码")
        private String depacode;
        @TableLogic
        @ApiModelProperty(value = "",hidden = true)
        private Boolean isDel;
}
