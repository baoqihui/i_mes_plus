package com.rh.i_mes_plus.model.iqc;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 抽样方案明细
 *
 * @author hqb
 * @date 2020-10-16 11:39:51
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("iqc_co_qc_plan")
public class IqcCoQcPlan extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "抽样方案ID")
        @ApiModelProperty(value = "抽样方案ID")
        private Long ottId;
        @Excel(name = "样本大小代码")
        @ApiModelProperty(value = "样本大小代码")
        private String code;
        @Excel(name = "样本数")
        @ApiModelProperty(value = "样本数")
        private Long count;
        @Excel(name = "AQL值")
        @ApiModelProperty(value = "AQL值")
        private Double aqlValue;
        @Excel(name = "AC值")
        @ApiModelProperty(value = "AC值")
        private String acValue;
        @Excel(name = "RE值")
        @ApiModelProperty(value = "RE值")
        private String reValue;
        @Excel(name = "机构代码")
        @ApiModelProperty(value = "机构代码")
        private String depaCode;
        @Excel(name = "部门名称")
        @ApiModelProperty(value = "部门名称")
        private String depaName;
}