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
 * 条码类型
 *
 * @author hbq
 * @date 2021-06-28 18:41:07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_barcode_type")
public class SmsBarcodeType extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "类型代码")
        @ApiModelProperty(value = "类型代码")
        private String typeCode;
        @Excel(name = "类型名称")
        @ApiModelProperty(value = "类型名称")
        private String typeName;
}
