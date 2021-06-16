package com.rh.i_mes_plus.model.sms;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 入库单表
 *
 * @author hqb
 * @date 2020-10-08 10:44:31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_wms_receive_doc")
public class SmsWmsReceiveDoc extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "入库单号")
        @ApiModelProperty(value = "入库单号")
        private String wrDocNum;
        @Excel(name = "收料日期(计划收料日期)",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "收料日期(计划收料日期)")
        private Date wrDate;
        @Excel(name = "单据类型代码")
        @ApiModelProperty(value = "单据类型代码")
        private String dtCode;
        @Excel(name = "客户")
        @ApiModelProperty(value = "客户")
        private String customer;
        @Excel(name = "供应商代码")
        @ApiModelProperty(value = "供应商代码")
        private String supplierCode;
        @Excel(name = "录入人")
        @ApiModelProperty(value = "录入人")
        private String wrInputNo;
        @Excel(name = "录入时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "录入时间")
        private Date wrInputDate;
        @Excel(name = "1=录入、2=入库中、3=已审核、4=关结")
        @ApiModelProperty(value = "1=录入、2=入库中、3=已审核、4=关结")
        private String wrState;
        @Excel(name = "关结人")
        @ApiModelProperty(value = "关结人")
        private String wrCloseNo;
        @Excel(name = "关结时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "关结时间")
        private Date wrCloseDate;
        @Excel(name = "备注")
        @ApiModelProperty(value = "备注")
        private String wrRemark;
        @Excel(name = "仓库代码")
        @ApiModelProperty(value = "仓库代码")
        private String whCode;
        @Excel(name = "收料单类型(0表示客供料、1表示自购料、2表示集团调拨料、3表示杂收物料、4表示超领物料、5产品入库单、6客户退货单)")
        @ApiModelProperty(value = "收料单类型(0表示客供料、1表示自购料、2表示集团调拨料、3表示杂收物料、4表示超领物料、5产品入库单、6客户退货单)")
        private String wrType;
        @Excel(name = "ERP单号(同步时写入)")
        @ApiModelProperty(value = "ERP单号(同步时写入)")
        private String erpDoc;
        @Excel(name = "ERP单据类型(同步时写入)")
        @ApiModelProperty(value = "ERP单据类型(同步时写入)")
        private String erpDocType;
        @Excel(name = "工单号")
        @ApiModelProperty(value = "工单号")
        private String projectId;
        @Excel(name = "检验状态(0未送检1已送检2未接检3检验中4允收5拒收5让步接收)")
        @ApiModelProperty(value = "检验状态(0未送检1已送检2未接检3检验中4允收5拒收5让步接收)")
        private String oqaFlag;
        @Excel(name = "结算标志(Y是N否)，默认否")
        @ApiModelProperty(value = "结算标志(Y是N否)，默认否")
        private String settleAccounts;
        @Excel(name = "采购进货标识（Y:采购，N：杂收）")
        @ApiModelProperty(value = "采购进货标识（Y:采购，N：杂收）")
        private String mixFlag;
        @Excel(name = "标志是否回写erp（Y：已回写  N：没有）")
        @ApiModelProperty(value = "标志是否回写erp（Y：已回写  N：没有）")
        private String returnErpFlag;
        @Excel(name = "收发类型ID")
        @ApiModelProperty(value = "收发类型ID")
        private Long twtDid;
        @Excel(name = "BS打印标志 1已打印 0 未打印")
        @ApiModelProperty(value = "BS打印标志 1已打印 0 未打印")
        private String printFlag;
        @Excel(name = "关联出库单")
        @ApiModelProperty(value = "关联出库单")
        private String outDocId;
        @Excel(name = "部门代码")
        @ApiModelProperty(value = "部门代码")
        private String depaCode;
        @Excel(name = "部门名称")
        @ApiModelProperty(value = "部门名称")
        private String depaName;
}
