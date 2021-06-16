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
 * @date 2020-12-16 14:55:14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_wms_reload_doc")
public class SmsWmsReloadDoc extends SuperEntity {
    private static final long serialVersionUID=1L;
    
    @Excel(name = "换料单号")
    @ApiModelProperty(value = "换料单号")
    private String reloadNo;
    @Excel(name = "单据状态:0-开立 1-退料中4-关结")
    @ApiModelProperty(value = "单据状态:0-开立 1-退料中4-关结")
    private String reloadStatus;
    @Excel(name = "单据日期",format="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "单据日期")
    private Date reloadDate;
    @Excel(name = "供应商编号")
    @ApiModelProperty(value = "供应商编号")
    private String supplierCode;
    @Excel(name = "供应商名称")
    @ApiModelProperty(value = "供应商名称")
    private String supplierName;
    @Excel(name = "发货仓库")
    @ApiModelProperty(value = "发货仓库")
    private String whCode;
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
}
