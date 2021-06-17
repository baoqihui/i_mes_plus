package com.rh.i_mes_plus.model.iqc;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 错误代码
 *
 * @author hbq
 * @date 2020-10-23 08:51:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("iqc_co_error_code")
public class IqcCoErrorCode extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "故障代码")
        @ApiModelProperty(value = "故障代码")
        private String errorCode;
        @Excel(name = "故障类型")
        @ApiModelProperty(value = "故障类型")
        private String errorType;
        @Excel(name = "故障描述")
        @ApiModelProperty(value = "故障描述")
        private String errorDesc;
        @Excel(name = "缺陷等级")
        @ApiModelProperty(value = "缺陷等级")
        private String odlCode;
        @Excel(name = "机构代码")
        @ApiModelProperty(value = "机构代码")
        private String depaCode;
        @Excel(name = "部门名称")
        @ApiModelProperty(value = "部门名称")
        private String depaName;
}
