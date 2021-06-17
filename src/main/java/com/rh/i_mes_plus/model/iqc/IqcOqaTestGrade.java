package com.rh.i_mes_plus.model.iqc;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 检验水平
 *
 * @author hqb
 * @date 2020-10-16 13:20:30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("iqc_oqa_test_grade")
public class IqcOqaTestGrade extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "检验水准ID")
        @ApiModelProperty(value = "检验水准ID")
        private Long olId;
        @Excel(name = "检验水平 (S-1,S-2)")
        @ApiModelProperty(value = "检验水平 (S-1,S-2)")
        private String otgLevel;
        @Excel(name = "部门代码")
        @ApiModelProperty(value = "部门代码")
        private String depaCode;
        @Excel(name = "部门名称")
        @ApiModelProperty(value = "部门名称")
        private String depaName;
}
