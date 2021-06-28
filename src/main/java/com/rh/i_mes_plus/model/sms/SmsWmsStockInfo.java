package com.rh.i_mes_plus.model.sms;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 库存信息表(修改此表：如果在库数量为0时，需要把相关信息保存至表T_WMS_STOCK_ZERO_INFO，退料时才能得到SN原有的相关信息)
 *
 * @author hbq
 * @date 2020-12-09 10:11:55
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_wms_stock_info")
public class SmsWmsStockInfo extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "单据号")
        @ApiModelProperty(value = "单据号")
        private String docNum;
        @Excel(name = "客户代码")
        @ApiModelProperty(value = "客户代码")
        private String custCode;
        @Excel(name = "物料编码")
        @ApiModelProperty(value = "物料编码")
        private String coItemCode;
        @Excel(name = "")
        @ApiModelProperty(value = "")
        private String productVer;
        @Excel(name = "生产批次号")
        @ApiModelProperty(value = "生产批次号")
        private String lotNumber;
        @Excel(name = "机器序列号")
        @ApiModelProperty(value = "机器序列号")
        private String tblBarcode;
        @Excel(name = "箱号")
        @ApiModelProperty(value = "箱号")
        private String container;
        @Excel(name = "数量")
        @ApiModelProperty(value = "数量")
        private Long amount;
        @Excel(name = "订单号/采购单号")
        @ApiModelProperty(value = "订单号/采购单号")
        private String orderNo;
        @Excel(name = "仓库SN")
        @ApiModelProperty(value = "仓库SN")
        private String whCode;
        @Excel(name = "库区SN")
        @ApiModelProperty(value = "库区SN")
        private String reservoirCode;
        @Excel(name = "库位")
        @ApiModelProperty(value = "库位")
        private String areaSn;
        @Excel(name = "品质标识Y:良品 N:不良品")
        @ApiModelProperty(value = "品质标识Y:良品 N:不良品")
        private String quality;
        @Excel(name = "退料标识0:否 1:是")
        @ApiModelProperty(value = "退料标识0:否 1:是")
        private String isReturn;
        @Excel(name = "供应商代码")
        @ApiModelProperty(value = "供应商代码")
        private String supplierCode;
        @Excel(name = "制造商生产日期",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "制造商生产日期")
        private Date pdate;
        @Excel(name = "冻结标志（Y冻结/N解冻）")
        @ApiModelProperty(value = "冻结标志（Y冻结/N解冻）")
        private String freezeFlag;
        @Excel(name = "检验状态:Y为已检 N为未检")
        @ApiModelProperty(value = "检验状态:Y为已检 N为未检")
        private String haveCheck;
        @Excel(name = "")
        @ApiModelProperty(value = "")
        private String subatch;
        @Excel(name = "入库时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "入库时间")
        private Date inStockTime;
        @Excel(name = "入库人")
        @ApiModelProperty(value = "入库人")
        private String inStockMan;
        @Excel(name = "库存状态（1：在库，2：已备料，3：调拨，4盘点）")
        @ApiModelProperty(value = "库存状态（1：在库，2：已备料，3：调拨，4盘点）")
        private String stockFlag;
        @Excel(name = "出库单号")
        @ApiModelProperty(value = "出库单号")
        private String outStockDoc;
        @Excel(name = "版本")
        @ApiModelProperty(value = "版本")
        private String editionCode;
        @Excel(name = "工单号")
        @ApiModelProperty(value = "工单号")
        private String projectId;
        @Excel(name = "部门代码")
        @ApiModelProperty(value = "部门代码")
        private String depaCode;
        @Excel(name = "部门名称")
        @ApiModelProperty(value = "部门名称")
        private String depaName;
}
