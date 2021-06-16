package com.rh.i_mes_plus.model.sms;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 采购订单明细
 *
 * @author hqb
 * @date 2020-09-30 13:12:05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_wms_po_det")
public class SmsWmsPoDet extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "采购单号")
        @ApiModelProperty(value = "采购单号")
        private String twdPoNo;
        @Excel(name = "项次")
        @ApiModelProperty(value = "项次")
        private Long twdPoItemNo;
        @Excel(name = "交期",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "交期")
        private Date twdDate;
        @Excel(name = "物料代码")
        @ApiModelProperty(value = "物料代码")
        private String itemCode;
        @Excel(name = "物料描述")
        @ApiModelProperty(value = "物料描述")
        private String itemName;
        @Excel(name = "最小包装数")
        @ApiModelProperty(value = "最小包装数")
        private Long itemMinPack;
        @Excel(name = "订单数量")
        @ApiModelProperty(value = "订单数量")
        private Long twdPoQty;
        @Excel(name = "采购累计交货数量（已收）")
        @ApiModelProperty(value = "采购累计交货数量（已收）")
        private Long twdReceiveQty;
        @Excel(name = "mes已收数量")
        @ApiModelProperty(value = "mes已收数量")
        private Long mesReceiveQty;
        @Excel(name = "制造商代码")
        @ApiModelProperty(value = "制造商代码")
        private String manufacturerCode;
        @Excel(name = "制造商料号")
        @ApiModelProperty(value = "制造商料号")
        private String mpn;
        @Excel(name = "mes关结标识")
        @ApiModelProperty(value = "mes关结标识")
        private String twdIsClose;
        @Excel(name = "sap关结标识")
        @ApiModelProperty(value = "sap关结标识")
        private String erpIsClose;
        @Excel(name = "备注")
        @ApiModelProperty(value = "备注")
        private String twdRemark;
        @Excel(name = "供应商编号")
        @ApiModelProperty(value = "供应商编号")
        private String twdSupCode;
        @Excel(name = "供应商名称")
        @ApiModelProperty(value = "供应商名称")
        private String twdSupName;
        @TableLogic
        @ApiModelProperty(value = "",hidden = true)
        private Boolean isDel;
        @Excel(name = "部门代码")
        @ApiModelProperty(value = "部门代码")
        private String depaCode;
        @Excel(name = "部门名称")
        @ApiModelProperty(value = "部门名称")
        private String depaName;
}
