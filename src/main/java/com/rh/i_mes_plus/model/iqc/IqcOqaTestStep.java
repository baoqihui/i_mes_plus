package com.rh.i_mes_plus.model.iqc;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 检验阶
 *
 * @author hqb
 * @date 2020-10-16 11:39:51
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("iqc_oqa_test_step")
public class IqcOqaTestStep extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "检验阶代码")
        @ApiModelProperty(value = "检验阶代码")
        private String otsCode;
        @Excel(name = "检验项目 IQC、FQC、OQC")
        @ApiModelProperty(value = "检验项目 IQC、FQC、OQC")
        private String otsName;
        @Excel(name = "启用标识(0未用/1启用)")
        @ApiModelProperty(value = "启用标识(0未用/1启用)")
        private Boolean isUse;
        @Excel(name = "机构代码")
        @ApiModelProperty(value = "机构代码")
        private String depaCode;
        @Excel(name = "部门名称")
        @ApiModelProperty(value = "部门名称")
        private String depaName;
}
