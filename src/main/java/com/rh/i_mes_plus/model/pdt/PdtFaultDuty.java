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
 * 不良责任
 *
 * @author hbq
 * @date 2021-07-05 10:11:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pdt_fault_duty")
public class PdtFaultDuty extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "不良责任代码")
        @ApiModelProperty(value = "不良责任代码")
        private String dutyCode;
        @Excel(name = "不良责任名称")
        @ApiModelProperty(value = "不良责任名称")
        private String dutyName;
}
