package com.rh.i_mes_plus.model.pdt;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import com.rh.i_mes_plus.common.model.SuperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 不良原因
 *
 * @author hbq
 * @date 2021-07-05 10:11:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pdt_fault_reason_code")
public class PdtFaultReasonCode extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "不良原因代码")
        @ApiModelProperty(value = "不良原因代码")
        private String reasonCode;
        @Excel(name = "维修方法代码")
        @ApiModelProperty(value = "维修方法代码")
        private String repairCode;
        @Excel(name = "不良原因")
        @ApiModelProperty(value = "不良原因")
        private String reasonName;
}
