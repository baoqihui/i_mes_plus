package com.rh.i_mes_plus.model.pdt;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 成品出库详情表
 *
 * @author hbq
 * @date 2021-01-06 15:22:23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pdt_wms_out_stock_detail")
public class PdtWmsOutStockDetail extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "出库单据号")
        @ApiModelProperty(value = "出库单据号")
        private String docNo;
        @Excel(name = "出库料号")
        @ApiModelProperty(value = "出库料号")
        private String modelCode;
        @Excel(name = "品名")
        @ApiModelProperty(value = "品名")
        private String modelName;
        @Excel(name = "销售单号")
        @ApiModelProperty(value = "销售单号")
        private String saleOrder;
        @Excel(name = "行项目")
        @ApiModelProperty(value = "行项目")
        private String poLine;
        @Excel(name = "客户订单号")
        @ApiModelProperty(value = "客户订单号")
        private String custOrder;
        @Excel(name = "计划出库数量")
        @ApiModelProperty(value = "计划出库数量")
        private Long planNum;
        @Excel(name = "实际出库数量")
        @ApiModelProperty(value = "实际出库数量")
        private Long receiveNum;
        @Excel(name = "备注")
        @ApiModelProperty(value = "备注")
        private String remark;
        @Excel(name = "供应商代码")
        @ApiModelProperty(value = "供应商代码")
        private String supplierCode;
        @Excel(name = "SAP出货单")
        @ApiModelProperty(value = "SAP出货单")
        private String sapOutStockDoc;
        @Excel(name = "SAP出货单行号")
        @ApiModelProperty(value = "SAP出货单行号")
        private String sapOutStockLine;
}
