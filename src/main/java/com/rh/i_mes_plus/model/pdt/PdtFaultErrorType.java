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
 * 不良类型
 *
 * @author hbq
 * @date 2021-07-05 10:11:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pdt_fault_error_type")
public class PdtFaultErrorType extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "不良类型")
        @ApiModelProperty(value = "不良类型")
        private String errorType;
        @Excel(name = "不良类型描述")
        @ApiModelProperty(value = "不良类型描述")
        private String errorTypeName;
}
