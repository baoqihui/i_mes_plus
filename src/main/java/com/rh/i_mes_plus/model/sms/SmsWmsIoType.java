package com.rh.i_mes_plus.model.sms;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 单据类型表
 *
 * @author hbq
 * @date 2021-01-19 10:01:03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_wms_io_type")
public class SmsWmsIoType extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "单据类型代码")
        @ApiModelProperty(value = "单据类型代码")
        private String dtCode;
        @Excel(name = "")
        @ApiModelProperty(value = "")
        private String dtName;
        @Excel(name = "")
        @ApiModelProperty(value = "")
        private String type;
        @Excel(name = "")
        @ApiModelProperty(value = "")
        private String repCode;
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
