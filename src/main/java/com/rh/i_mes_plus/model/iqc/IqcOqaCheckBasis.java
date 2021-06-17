package com.rh.i_mes_plus.model.iqc;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * OQA检验依据
 *
 * @author hqb
 * @date 2020-10-16 11:39:51
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("iqc_oqa_check_basis")
public class IqcOqaCheckBasis extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "检验依据")
        @ApiModelProperty(value = "检验依据")
        private String memo;
        @Excel(name = "默认标识(1默认/0非默认)")
        @ApiModelProperty(value = "默认标识(1默认/0非默认)")
        private Boolean isDef;
        @Excel(name = "抽样阶(iqc,oqc)")
        @ApiModelProperty(value = "抽样阶(iqc,oqc)")
        private String otsCode;
        @Excel(name = "部门代码")
        @ApiModelProperty(value = "部门代码")
        private String depaCode;
        @Excel(name = "部门名称")
        @ApiModelProperty(value = "部门名称")
        private String depaName;
}
