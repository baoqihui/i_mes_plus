package com.rh.i_mes_plus.model.sms;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * BOM料件制造商表
 *
 * @author hbq
 * @date 2020-10-31 15:56:15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_co_manufacturer")
public class SmsCoManufacturer extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "制造商名称")
        @ApiModelProperty(value = "制造商名称")
        private String manufacturerName;
        @Excel(name = "制造商编号")
        @ApiModelProperty(value = "制造商编号")
        private String manufacturerCode;
        @Excel(name = "manufacturer_ID")
        @ApiModelProperty(value = "manufacturer_ID")
        private Long manufacturerId;
        @Excel(name = "制造商简称")
        @ApiModelProperty(value = "制造商简称")
        private String manufacturerAbbreviation;
        @Excel(name = "制造商登录密码(WEB打印)")
        @ApiModelProperty(value = "制造商登录密码(WEB打印)")
        private String manufacturerPass;
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
