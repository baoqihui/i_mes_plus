package com.rh.i_mes_plus.model.sms;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 调拨单
 *
 * @author hbq
 * @date 2020-12-11 13:39:17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_wms_move_doc")
public class SmsWmsMoveDoc extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "调拨单号")
        @ApiModelProperty(value = "调拨单号")
        private String moveNo;
        @Excel(name = "单据状态:0-开立 1-调拨中4-关结")
        @ApiModelProperty(value = "单据状态:0-开立 1-调拨中4-关结")
        private String moveStatus;
        @Excel(name = "1调拨2借出")
        @ApiModelProperty(value = "1调拨2借出")
        private String moveFlag;
        @Excel(name = "计划调拨日期",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "调入仓库sn")
        private String inWhCode;
        @Excel(name = "调出仓库sn")
        @ApiModelProperty(value = "调出仓库sn")
        private String outWhCode;
        private Date moveDate;
        @Excel(name = "出库单号")
        @ApiModelProperty(value = "出库单号")
        private String outDocId;
        @Excel(name = "供应商编号")
        @ApiModelProperty(value = "供应商编号")
        private String supplierCode;
        @Excel(name = "erp单号(同步时写入)")
        @ApiModelProperty(value = "erp单号(同步时写入)")
        private String erpDoc;
        @Excel(name = "erp单据类型(同步时写入)")
        @ApiModelProperty(value = "erp单据类型(同步时写入)")
        private String erpDocType;
        @Excel(name = "开单人工号")
        @ApiModelProperty(value = "开单人工号")
        private String createNo;
        @Excel(name = "备注")
        @ApiModelProperty(value = "备注")
        private String remark;
        @Excel(name = "部门代码")
        @ApiModelProperty(value = "部门代码")
        private String depaCode;
        @Excel(name = "部门名称")
        @ApiModelProperty(value = "部门名称")
        private String depaName;
        @Excel(name = "调拨单类型(1,非半成品 2，半成品)")
        @ApiModelProperty(value = "调拨单类型(1,非半成品 2，半成品)")
        private String moveType;
        
}
