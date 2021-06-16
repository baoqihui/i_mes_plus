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
 * 采购订单
 *
 * @author hqb
 * @date 2020-09-30 13:12:05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_wms_po")
public class SmsWmsPo extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "采购单号")
        @ApiModelProperty(value = "采购单号")
        private String poNo;
        @Excel(name = "状态:1=开立、2=采购中、4=关结")
        @ApiModelProperty(value = "状态:1=开立、2=采购中、4=关结")
        private String poState;
        @Excel(name = "单据日期",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "单据日期")
        private Date poDate;
        @Excel(name = "供应商代码")
        @ApiModelProperty(value = "供应商代码")
        private String supCode;
        @Excel(name = "供应商名称")
        @ApiModelProperty(value = "供应商名称")
        private String supName;
        @Excel(name = "开单人工号")
        @ApiModelProperty(value = "开单人工号")
        private String createrNo;
        @Excel(name = "开单人")
        @ApiModelProperty(value = "开单人")
        private String createrName;
        @Excel(name = "ERP终止标志(Y是N否)")
        @ApiModelProperty(value = "ERP终止标志(Y是N否)")
        private String erpEnd;
        @Excel(name = "备注")
        @ApiModelProperty(value = "备注")
        private String remark;
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
