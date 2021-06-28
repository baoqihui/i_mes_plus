package com.rh.i_mes_plus.model.sms;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 出库单主表
 *
 * @author hbq
 * @date 2020-11-02 15:05:25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_wms_out_stock_doc")
public class SmsWmsOutStockDoc extends SuperEntity {
    private static final long serialVersionUID=1L;
        @Excel(name = "出库单据号")
        @ApiModelProperty(value = "出库单据号")
        private String docNo;
        @Excel(name = "客户代码")
        @ApiModelProperty(value = "客户代码")
        private String custCode;
        @Excel(name = "计划出货日期",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "计划出货日期")
        private Date docOutTime;
        @Excel(name = "开单时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "开单时间")
        private Date docTime;
        @Excel(name = "制单人")
        @ApiModelProperty(value = "制单人")
        private String docManNo;
        @Excel(name = "出库类型(关联单据类型表ID) t_co_wh_doc_type")
        @ApiModelProperty(value = "出库类型(关联单据类型表ID) t_co_wh_doc_type")
        private String dtCode;
        @Excel(name = "发货地址")
        @ApiModelProperty(value = "发货地址")
        private String docShipAddress;
        @Excel(name = "运单号")
        @ApiModelProperty(value = "运单号")
        private String docShipAccount;
        @Excel(name = "状态(1开立、2备货中、3备货完成 、4关结)")
        @ApiModelProperty(value = "状态(1开立、2备货中、3备货完成 、4关结)")
        private String docStatus;
        @Excel(name = "关结时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "关结时间")
        private Date docCloseDate;
        @Excel(name = "运单类型 1 收货人 2 第三方")
        @ApiModelProperty(value = "运单类型 1 收货人 2 第三方")
        private Long docShipType;
        @Excel(name = "供应商代码")
        @ApiModelProperty(value = "供应商代码")
        private String supplierCode;
        @Excel(name = "供应商名称")
        @ApiModelProperty(value = "供应商名称")
        private String supplierName;
        @Excel(name = "收发类型ID")
        @ApiModelProperty(value = "收发类型ID")
        private Long twtDid;
        @Excel(name = "仓库代码")
        @ApiModelProperty(value = "仓库代码")
        private String whCode;
        @Excel(name = "备料类型：1手工创建 2自EXCEL 3自料站表  4自生产工单")
        @ApiModelProperty(value = "备料类型：1手工创建 2自EXCEL 3自料站表  4自生产工单")
        private String pickupType;
        @Excel(name = "OQC确认时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "OQC确认时间")
        private Date oqcTime;
        @Excel(name = "OQC确认人")
        @ApiModelProperty(value = "OQC确认人")
        private String oqcManNo;
        @Excel(name = "OQC标志:Y是N否默认否")
        @ApiModelProperty(value = "OQC标志:Y是N否默认否")
        private String oqcAccountsFlag;
        @Excel(name = "是否回写，Y为是；N/null为否")
        @ApiModelProperty(value = "是否回写，Y为是；N/null为否")
        private String toErp;
        @Excel(name = "备注")
        @ApiModelProperty(value = "备注")
        private String docRemark;
        @Excel(name = "部门代码")
        @ApiModelProperty(value = "部门代码")
        private String depaCode;
        @Excel(name = "部门名称")
        @ApiModelProperty(value = "部门名称")
        private String depaName;
        @Excel(name = "灯颜色ID")
        @ApiModelProperty(value = "灯颜色ID")
        private Long colorId;
}
