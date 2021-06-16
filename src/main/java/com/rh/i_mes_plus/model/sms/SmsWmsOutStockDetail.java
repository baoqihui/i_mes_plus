package com.rh.i_mes_plus.model.sms;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 出库物料表
 *
 * @author hbq
 * @date 2020-11-02 15:05:25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_wms_out_stock_detail")
public class SmsWmsOutStockDetail extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "出库单据号")
        @ApiModelProperty(value = "出库单据号")
        private String docNo;
        @Excel(name = "出库料号")
        @ApiModelProperty(value = "出库料号")
        private String itemCode;
        @Excel(name = "品名")
        @ApiModelProperty(value = "品名")
        private String itemName;
        @Excel(name = "规格")
        @ApiModelProperty(value = "规格")
        private String itemSpec;
        @Excel(name = "计划出库数量")
        @ApiModelProperty(value = "计划出库数量")
        private Long osdAmountPlan;
        @Excel(name = "实际出库数量")
        @ApiModelProperty(value = "实际出库数量")
        private Long osdAmountReal;
        @Excel(name = "订单号")
        @ApiModelProperty(value = "订单号")
        private Long osOrderDid;
        @Excel(name = "销售订单号")
        @ApiModelProperty(value = "销售订单号")
        private String osdOrder;
        @Excel(name = "客户订单号")
        @ApiModelProperty(value = "客户订单号")
        private String osdPoNo;
        @Excel(name = "行号")
        @ApiModelProperty(value = "行号")
        private Long osdLineNo;
        @Excel(name = "客户行号")
        @ApiModelProperty(value = "客户行号")
        private String osdPoLine;
        @Excel(name = "第几次发货")
        @ApiModelProperty(value = "第几次发货")
        private Long osdSerialNo;
        @Excel(name = "订单量")
        @ApiModelProperty(value = "订单量")
        private Long osdAmountOrder;
        @Excel(name = "备注")
        @ApiModelProperty(value = "备注")
        private String remark;
        @Excel(name = "单位")
        @ApiModelProperty(value = "单位")
        private String unit;
        @Excel(name = "启用FIFO")
        @ApiModelProperty(value = "启用FIFO")
        private String osdFifoFlag;
        @Excel(name = "OQC状态 0、正常 1、允收 2、拒收 3、批退审批 4、批退 5、让步审批 6、让步接收")
        @ApiModelProperty(value = "OQC状态 0、正常 1、允收 2、拒收 3、批退审批 4、批退 5、让步审批 6、让步接收")
        private Long oqcState;
        @Excel(name = "转出仓库")
        @ApiModelProperty(value = "转出仓库")
        private String fromWhCode;
        @Excel(name = "转入仓库")
        @ApiModelProperty(value = "转入仓库")
        private String toWhCode;
        @Excel(name = "成品料号")
        @ApiModelProperty(value = "成品料号")
        private String productItemCode;
        @Excel(name = "工单号")
        @ApiModelProperty(value = "工单号")
        private String projectId;
        @Excel(name = "版本")
        @ApiModelProperty(value = "版本")
        private String editionCode;
        @Excel(name = "供应商代码")
        @ApiModelProperty(value = "供应商代码")
        private String supplierCode;
        @Excel(name = "供应商名称")
        @ApiModelProperty(value = "供应商名称")
        private String supplierName;
        @Excel(name = "采购单号")
        @ApiModelProperty(value = "采购单号")
        private String erpPo;
        @Excel(name = "项次")
        @ApiModelProperty(value = "项次")
        private Long erpPoItemNo;
        @Excel(name = "计划出库数量(SMT理论计划出库数量)")
        @ApiModelProperty(value = "计划出库数量(SMT理论计划出库数量)")
        private Long packAmountPlan;
        @Excel(name = "销售订单别")
        @ApiModelProperty(value = "销售订单别")
        private String osdOrderType;
        @Excel(name = "采购入库单号")
        @ApiModelProperty(value = "采购入库单号")
        private String osdRevDoc;
        @Excel(name = "SMT领料调拨中扣减线边仓数量")
        @ApiModelProperty(value = "SMT领料调拨中扣减线边仓数量")
        private Long osdSmtOutAmount;
        @Excel(name = "未知-料站表ID")
        @ApiModelProperty(value = "未知-料站表ID")
        private Long materieldetailid;
        @Excel(name = "SAP出货单")
        @ApiModelProperty(value = "SAP出货单")
        private String sapOutStockDoc;
        @Excel(name = "SAP出库单行号")
        @ApiModelProperty(value = "SAP出库单行号")
        private Long sapOutStockLine;
        @Excel(name = "成本中心")
        @ApiModelProperty(value = "成本中心")
        private String costStation;
        @Excel(name = "未清数量")
        @ApiModelProperty(value = "未清数量")
        private Long uncleared;
        @Excel(name = "是否关闭")
        @ApiModelProperty(value = "是否关闭")
        private String osdIsClose;
        @Excel(name = "使用可用")
        @ApiModelProperty(value = "使用可用")
        private String osdIsUse;
        @Excel(name = "替代码")
        @ApiModelProperty(value = "替代码")
        private String replaceFlag;
}
