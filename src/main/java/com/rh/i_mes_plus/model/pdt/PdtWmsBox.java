package com.rh.i_mes_plus.model.pdt;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 
 *
 * @author hbq
 * @date 2020-12-30 13:14:06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pdt_wms_box")
public class PdtWmsBox extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "指令单号")
        @ApiModelProperty(value = "指令单号")
        private String moNo;
        @Excel(name = "箱号")
        @ApiModelProperty(value = "箱号")
        private String boxNo;
        @Excel(name = "批次")
        @ApiModelProperty(value = "批次")
        private String batch;
        @Excel(name = "成品料号")
        @ApiModelProperty(value = "成品料号")
        private String modelCode;
        @Excel(name = "成品名")
        @ApiModelProperty(value = "成品名")
        private String modelName;
        @Excel(name = "成品版本")
        @ApiModelProperty(value = "成品版本")
        private String modelVer;
        @Excel(name = "GT号")
        @ApiModelProperty(value = "GT号")
        private String gtNo;
        @Excel(name = "MPN")
        @ApiModelProperty(value = "MPN")
        private String mpn;
        @Excel(name = "总数")
        @ApiModelProperty(value = "总数")
        private Long boxQty;
        @Excel(name = "最大容量")
        @ApiModelProperty(value = "最大容量")
        private Long maxQty;
        @Excel(name = "创建人")
        @ApiModelProperty(value = "创建人")
        private String createName;
        @Excel(name = "重量")
        @ApiModelProperty(value = "重量")
        private BigDecimal weight;
}
