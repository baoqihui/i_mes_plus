package com.rh.i_mes_plus.model.sms;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 条码信息(临时)
 *
 * @author hqb
 * @date 2020-10-08 15:46:41
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_wms_barcode_info_log")
public class SmsWmsBarcodeInfoLog extends SuperEntity {
    private static final long serialVersionUID=1L;
        @Excel(name = "条码")
        @ApiModelProperty(value = "条码")
        private String tblBarcode;
        @Excel(name = "MPN")
        @ApiModelProperty(value = "MPN")
        private String mpn;
        @Excel(name = "数量  QTY")
        @ApiModelProperty(value = "数量  QTY")
        private Long tblNum;
        @Excel(name = "生成日 D/C",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "生成日 D/C")
        private Date tblCreatedate;
        @Excel(name = "制造商批号 LOT")
        @ApiModelProperty(value = "制造商批号 LOT")
        private String tblManufacturerBat;
        @Excel(name = "采购单号 PO")
        @ApiModelProperty(value = "采购单号 PO")
        private String tblPoNum;
        @Excel(name = "物料代码 ")
        @ApiModelProperty(value = "物料代码 ")
        private String tblItemcode;
        @Excel(name = "制造商代码 MFG")
        @ApiModelProperty(value = "制造商代码 MFG")
        private String tblManufacturerCode;
        @Excel(name = "供应商代码")
        @ApiModelProperty(value = "供应商代码")
        private String tblSupCode;
        @Excel(name = "入库单号")
        @ApiModelProperty(value = "入库单号")
        private String tblDocNum;
        @Excel(name = "批号")
        @ApiModelProperty(value = "批号")
        private String tblBatnum;
        @Excel(name = "制造商生产日期",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "制造商生产日期")
        private Date tblManufacturerDate;
        @Excel(name = "项次")
        @ApiModelProperty(value = "项次")
        private String tblItemSeq;
        @Excel(name = "来源 1 BS打印")
        @ApiModelProperty(value = "来源 1 BS打印")
        private String tblSource;
        @Excel(name = "顶级包装条码")
        @ApiModelProperty(value = "顶级包装条码")
        private String topBarcode;
        @Excel(name = "上级包装条码")
        @ApiModelProperty(value = "上级包装条码")
        private String upperBarcode;
        @Excel(name = "0 采购 1 赠品")
        @ApiModelProperty(value = "0 采购 1 赠品")
        private String giftFlag;
        @Excel(name = "项目号")
        @ApiModelProperty(value = "项目号")
        private String projectCode;
        @Excel(name = "湿敏等级")
        @ApiModelProperty(value = "湿敏等级")
        private String mcrMsdCode;

}
