package com.rh.i_mes_plus.model.sms;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 出库明细表
 *
 * @author hbq
 * @date 2020-11-02 15:05:25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_wms_out_stock_list")
public class SmsWmsOutStockList extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "出库单据号")
        @ApiModelProperty(value = "出库单据号")
        private String docNo;
        @Excel(name = "出库物料表id")
        @ApiModelProperty(value = "出库物料表id")
        private Long osdId;
        @Excel(name = "机种代码")
        @ApiModelProperty(value = "机种代码")
        private String coItemCode;
        @Excel(name = "生产批次号")
        @ApiModelProperty(value = "生产批次号")
        private String oslLotNumber;
        @Excel(name = "机器序列号")
        @ApiModelProperty(value = "机器序列号")
        private String tblBarcode;
        @Excel(name = "数量")
        @ApiModelProperty(value = "数量")
        private Long oslAmount;
        @Excel(name = "出库人")
        @ApiModelProperty(value = "出库人")
        private String outStockManNo;
        @Excel(name = "出库时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "出库时间")
        private Date outStockTime;
        @Excel(name = "包装阶名")
        @ApiModelProperty(value = "包装阶名")
        private String oslPackingName;
        @Excel(name = "装载容器")
        @ApiModelProperty(value = "装载容器")
        private String osContainer;
        @Excel(name = "客户订单号")
        @ApiModelProperty(value = "客户订单号")
        private String osOrder;
        @Excel(name = "接收人")
        @ApiModelProperty(value = "接收人")
        private String osRecNo;
        @Excel(name = "行号")
        @ApiModelProperty(value = "行号")
        private String lineNo;
        @Excel(name = "退料数量")
        @ApiModelProperty(value = "退料数量")
        private Long oslBackNum;
        @Excel(name = "成品料号")
        @ApiModelProperty(value = "成品料号")
        private String productItemCode;
        @Excel(name = "仓库SN")
        @ApiModelProperty(value = "仓库SN")
        private String whCode;
        @Excel(name = "库区SN")
        @ApiModelProperty(value = "库区SN")
        private String reservoirCode;
        @Excel(name = "仓库SN")
        @ApiModelProperty(value = "仓库SN")
        private String areaSn;
        @Excel(name = "栈板SN")
        @ApiModelProperty(value = "栈板SN")
        private String palletSn;
        @Excel(name = "供应商代码")
        @ApiModelProperty(value = "供应商代码")
        private String supplierCode;
        @Excel(name = "是否回写，Y为是；N/null为否")
        @ApiModelProperty(value = "是否回写，Y为是；N/null为否")
        private String isBaskErp;
        @Excel(name = "品质标识Y:良品 N:不良品 ")
        @ApiModelProperty(value = "品质标识Y:良品 N:不良品 ")
        private String skQualityFlag;
        @Excel(name = "领料单物料标志(Y是N否)，默认是")
        @ApiModelProperty(value = "领料单物料标志(Y是N否)，默认是")
        private String inDocFlag;
        @Excel(name = "VMI标识(Y是N否)")
        @ApiModelProperty(value = "VMI标识(Y是N否)")
        private String vmiFlag;
        @Excel(name = "允许上传ERP(Y是N否)")
        @ApiModelProperty(value = "允许上传ERP(Y是N否)")
        private String canToErp;
        @Excel(name = "版本")
        @ApiModelProperty(value = "版本")
        private String skEditionCode;
        @Excel(name = "工单号")
        @ApiModelProperty(value = "工单号")
        private String projectId;
        @Excel(name = "")
        @ApiModelProperty(value = "")
        private String remark;
}
