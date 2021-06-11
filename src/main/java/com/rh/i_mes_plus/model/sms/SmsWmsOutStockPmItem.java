package com.rh.i_mes_plus.model.sms;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工单和备料单绑定表
 *
 * @author hbq
 * @date 2020-11-02 17:19:51
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_wms_out_stock_pm_item")
public class SmsWmsOutStockPmItem extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "出库单据号")
        @ApiModelProperty(value = "出库单据号")
        private String docNo;
        @Excel(name = "工单号")
        @ApiModelProperty(value = "工单号")
        private String projectId;
        @Excel(name = "BOM料号")
        @ApiModelProperty(value = "BOM料号")
        private String itemCode;
        @Excel(name = "BOM品名")
        @ApiModelProperty(value = "BOM品名")
        private String itemName;
        @Excel(name = "BOM规格")
        @ApiModelProperty(value = "BOM规格")
        private String itemSpec;
        @Excel(name = "工单料号")
        @ApiModelProperty(value = "工单料号")
        private String projectItem;
        @Excel(name = "计划出库数量")
        @ApiModelProperty(value = "计划出库数量")
        private Long osdAmountPlan;
        @Excel(name = "实际出库数量")
        @ApiModelProperty(value = "实际出库数量")
        private Long osdAmount;
        @Excel(name = "erp回写标识")
        @ApiModelProperty(value = "erp回写标识")
        private String toErp;
        @Excel(name = "加工面(a单面 s 正面 c 反面)")
        @ApiModelProperty(value = "加工面(a单面 s 正面 c 反面)")
        private String scFlag;
        @Excel(name = "")
        @ApiModelProperty(value = "")
        private String outSeq;
        @Excel(name = "ERP行号")
        @ApiModelProperty(value = "ERP行号")
        private String erpItem;
        @Excel(name = "替代码")
        @ApiModelProperty(value = "替代码")
        private String replaceCode;
}
