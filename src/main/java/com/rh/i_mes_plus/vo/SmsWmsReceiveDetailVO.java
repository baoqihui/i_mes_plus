package com.rh.i_mes_plus.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.rh.i_mes_plus.model.sms.SmsWmsReceiveDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SmsWmsReceiveDetailVO extends SmsWmsReceiveDetail {
    @Excel(name = "交期",format="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "交期")
    private Date twdDate;
    @Excel(name = "物料描述")
    @ApiModelProperty(value = "物料描述")
    private String itemName;
    @Excel(name = "制造商代码 MFG")
    @ApiModelProperty(value = "制造商代码 MFG")
    private String manufacturerCode;
    @Excel(name = "制造商料号 ")
    @ApiModelProperty(value = "制造商料号 ")
    private String mpn;
    @Excel(name = "供应商编号")
    @ApiModelProperty(value = "供应商编号")
    private String twdSupCode;
}
