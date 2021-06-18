package com.rh.i_mes_plus.model.pdt;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 成品出库单主表
 *
 * @author hbq
 * @date 2021-01-06 15:22:23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pdt_wms_out_stock_doc")
public class PdtWmsOutStockDoc extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "出库单据号")
        @ApiModelProperty(value = "出库单据号")
        private String docNo;
        @Excel(name = "出库类型(关联单据类型表ID) t_co_wh_doc_type")
        @ApiModelProperty(value = "出库类型(关联单据类型表ID) t_co_wh_doc_type")
        private String dtCode;
        @Excel(name = "客户代码")
        @ApiModelProperty(value = "客户代码")
        private String custCode;
        @Excel(name = "备料日期",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "备料日期")
        private Date docOutTime;
        @Excel(name = "开单时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "开单时间")
        private Date docTime;
        @Excel(name = "制单人")
        @ApiModelProperty(value = "制单人")
        private String docManName;
        @Excel(name = "物流名称")
        @ApiModelProperty(value = "物流名称")
        private String docShipLogistics;
        @Excel(name = "发货地址")
        @ApiModelProperty(value = "发货地址")
        private String docShipAddress;
        @Excel(name = "运单号")
        @ApiModelProperty(value = "运单号")
        private String docShipAccount;
        @Excel(name = "状态(1录入、2、出库中 4、关结)")
        @ApiModelProperty(value = "状态(1录入、2、出库中 4、关结)")
        private String docStatus;
        @Excel(name = "关结时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "关结时间")
        private Date docCloseDate;
        @Excel(name = "供应商代码")
        @ApiModelProperty(value = "供应商代码")
        private String supplierCode;
        @Excel(name = "收发类型ID")
        @ApiModelProperty(value = "收发类型ID")
        private Long twtDid;
        @Excel(name = "仓库代码")
        @ApiModelProperty(value = "仓库代码")
        private String whCode;
        @Excel(name = "备注")
        @ApiModelProperty(value = "备注")
        private String docRemark;
        @Excel(name = "部门代码")
        @ApiModelProperty(value = "部门代码")
        private String depaCode;
        @Excel(name = "部门名称")
        @ApiModelProperty(value = "部门名称")
        private String depaName;
}
