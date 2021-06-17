package com.rh.i_mes_plus.model.iqc;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 抽样方案
(正常、加严、放宽)
 *
 * @author hqb
 * @date 2020-10-16 11:39:51
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("iqc_oqa_test_type")
public class IqcOqaTestType extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "检验阶代码")
        @ApiModelProperty(value = "检验阶代码")
        private String otsCode;
        @Excel(name = "方案名称")
        @ApiModelProperty(value = "方案名称")
        private String ottName;
        @Excel(name = "显示顺序")
        @ApiModelProperty(value = "显示顺序")
        private Integer sort;
        @Excel(name = "部门代码")
        @ApiModelProperty(value = "部门代码")
        private String depaCode;
        @Excel(name = "部门名称")
        @ApiModelProperty(value = "部门名称")
        private String depaName;
}
