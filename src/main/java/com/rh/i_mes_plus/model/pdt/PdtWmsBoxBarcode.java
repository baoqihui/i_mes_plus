package com.rh.i_mes_plus.model.pdt;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 *
 * @author hbq
 * @date 2020-12-28 13:28:45
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pdt_wms_box_barcode")
public class PdtWmsBoxBarcode extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "指令单号")
        @ApiModelProperty(value = "指令单号")
        private String moNo;
        @Excel(name = "箱号")
        @ApiModelProperty(value = "箱号")
        private String boxNo;
        @Excel(name = "条码")
        @ApiModelProperty(value = "条码")
        private String barcode;
}
