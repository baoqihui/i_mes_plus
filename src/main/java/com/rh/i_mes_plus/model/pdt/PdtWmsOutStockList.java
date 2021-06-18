package com.rh.i_mes_plus.model.pdt;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 成品出库明细表
 *
 * @author hbq
 * @date 2021-01-06 15:22:23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pdt_wms_out_stock_list")
public class PdtWmsOutStockList extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "出库单据号")
        @ApiModelProperty(value = "出库单据号")
        private String docNo;
        @Excel(name = "出库物料表id")
        @ApiModelProperty(value = "出库物料表id")
        private Long osdId;
        @Excel(name = "箱号")
        @ApiModelProperty(value = "箱号")
        private String boxNo;
        @Excel(name = "物料sn")
        @ApiModelProperty(value = "物料sn")
        private String barcode;
        @Excel(name = "物料编码")
        @ApiModelProperty(value = "物料编码")
        private String modelCode;
        @Excel(name = "生产批次号")
        @ApiModelProperty(value = "生产批次号")
        private String modelName;
        @Excel(name = "数量")
        @ApiModelProperty(value = "数量")
        private Long amount;
        @Excel(name = "出库人")
        @ApiModelProperty(value = "出库人")
        private String outStockManName;
        @Excel(name = "出库时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "出库时间")
        private Date outStockTime;
        @Excel(name = "")
        @ApiModelProperty(value = "")
        private String remark;
}
