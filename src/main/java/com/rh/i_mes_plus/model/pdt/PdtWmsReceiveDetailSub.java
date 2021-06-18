package com.rh.i_mes_plus.model.pdt;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 入库单明细表
 *
 * @author hbq
 * @date 2020-12-29 15:41:49
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pdt_wms_receive_detail_sub")
public class PdtWmsReceiveDetailSub extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "入库单号")
        @ApiModelProperty(value = "入库单号")
        private String docNo;
        @Excel(name = "入库单号")
        @ApiModelProperty(value = "入库单号")
        private Long rdId;
        @Excel(name = "箱号")
        @ApiModelProperty(value = "箱号")
        private String boxNo;
        @Excel(name = "条码")
        @ApiModelProperty(value = "条码")
        private String barcode;
        @Excel(name = "物料代码")
        @ApiModelProperty(value = "物料代码")
        private String modelCode;
        @Excel(name = "物料名称")
        @ApiModelProperty(value = "物料名称")
        private String modelName;
        @Excel(name = "批次")
        @ApiModelProperty(value = "批次")
        private String batch;
        @Excel(name = "备注")
        @ApiModelProperty(value = "备注")
        private String remark;
        @Excel(name = "部门代码")
        @ApiModelProperty(value = "部门代码")
        private String depaCode;
        @Excel(name = "部门名称")
        @ApiModelProperty(value = "部门名称")
        private String depaName;
        @Excel(name = "操作人")
        @ApiModelProperty(value = "操作人")
        private String receiveManName;
        @Excel(name = "数量")
        @ApiModelProperty(value = "数量")
        private Long amount;
}
