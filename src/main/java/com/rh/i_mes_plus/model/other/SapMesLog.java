package com.rh.i_mes_plus.model.other;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * sap-mes接口错误日志
 *
 * @author hbq
 * @date 2020-10-31 10:58:31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sap_mes_log")
public class SapMesLog extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "错误标识")
        @ApiModelProperty(value = "错误标识")
        private String errorFlag;
        @Excel(name = "错误接口")
        @ApiModelProperty(value = "错误接口")
        private String interfaces;
        @Excel(name = "(1,sap-mes 2,mes-sap)")
        @ApiModelProperty(value = "(1,sap-mes 2,mes-sap)")
        private Integer type;
        @Excel(name = "备注")
        @ApiModelProperty(value = "备注")
        private String remark;

    public SapMesLog() {
    }
    public SapMesLog(String errorFlag, String interfaces, Integer type, String remark) {
        this.errorFlag = errorFlag;
        this.interfaces = interfaces;
        this.type = type;
        this.remark = remark;
    }
}
