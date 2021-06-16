package com.rh.i_mes_plus.model.sms;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 挑料日志表
 *
 * @author hbq
 * @date 2021-03-01 14:31:55
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_wms_choose_barcode_log")
public class SmsWmsChooseBarcodeLog extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "备料单号")
        @ApiModelProperty(value = "备料单号")
        private String docNo;
        @Excel(name = "物料编号")
        @ApiModelProperty(value = "物料编号")
        private String itemCode;
        @Excel(name = "需求数量")
        @ApiModelProperty(value = "需求数量")
        private Long qtyPlan;
        @Excel(name = "挑料数量")
        @ApiModelProperty(value = "挑料数量")
        private Long qtyReal;
        @Excel(name = "条码")
        @ApiModelProperty(value = "条码")
        private String barcode;
        @Excel(name = "条码数量")
        @ApiModelProperty(value = "条码数量")
        private Long qtyBarcode;
        @Excel(name = "是否缺料")
        @ApiModelProperty(value = "是否缺料")
        private Boolean isLack;
        @Excel(name = "是否剪断")
        @ApiModelProperty(value = "是否剪断")
        private Boolean isSnip;
        @Excel(name = "是否已发")
        @ApiModelProperty(value = "是否已发")
        private Boolean isSend;
        @Excel(name = "灯光颜色")
        @ApiModelProperty(value = "灯光颜色")
        private Integer lightColor;
        @Excel(name = "库位")
        @ApiModelProperty(value = "库位")
        private String loc;
        @Excel(name = "备注")
        @ApiModelProperty(value = "备注")
        private String remark;
        @Excel(name = "创建人")
        @ApiModelProperty(value = "创建人")
        private String createUser;
}
