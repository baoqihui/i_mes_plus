package com.rh.i_mes_plus.model.sms;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 调拨单物料明细表
 *
 * @author hbq
 * @date 2020-12-16 14:55:14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_wms_reload_doc_detail")
public class SmsWmsReloadDocDetail extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "换料单号")
        @ApiModelProperty(value = "换料单号")
        private String reloadNo;
        @Excel(name = "采购单号")
        @ApiModelProperty(value = "采购单号")
        private String poNo;
        @Excel(name = "物料编码")
        @ApiModelProperty(value = "物料编码")
        private String itemCode;
        @Excel(name = "物料名称")
        @ApiModelProperty(value = "物料名称")
        private String itemName;
        @Excel(name = "物料描述")
        @ApiModelProperty(value = "物料描述")
        private String itemSpec;
        @Excel(name = "申请数量")
        @ApiModelProperty(value = "申请数量")
        private Long totalAmount;
        @Excel(name = "实发数量")
        @ApiModelProperty(value = "实发数量")
        private Long realityAmount;
        @Excel(name = "已收数量")
        @ApiModelProperty(value = "已收数量")
        private Long receiveAmount;
        @Excel(name = "备注")
        @ApiModelProperty(value = "备注")
        private String remark;
        @Excel(name = "供应商编号")
        @ApiModelProperty(value = "供应商编号")
        private String supplierCode;
        @Excel(name = "供应商名称")
        @ApiModelProperty(value = "供应商名称")
        private String supplierName;
}
