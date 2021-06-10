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
 * 客户地址
 *
 * @author hqb
 * @date 2020-09-21 08:41:57
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_customer_address")
public class UmsCustomerAddress extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "客户编码")
        @ApiModelProperty(value = "客户编码")
        private String custCode;
        @Excel(name = "客户地址")
        @ApiModelProperty(value = "客户地址")
        private String customerAddress;
        @Excel(name = "",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "")
        private Date createDate;
        @TableLogic
        @ApiModelProperty(value = "",hidden = true)
        private Boolean isDel;
}
