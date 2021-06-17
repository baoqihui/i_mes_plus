package com.rh.i_mes_plus.model.iqc;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 检测项目
 *
 * @author hqb
 * @date 2020-10-16 11:39:51
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("iqc_test_item")
public class IqcTestItem extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "项目类型ID")
        @ApiModelProperty(value = "项目类型ID")
        private Long tiyId;
        @Excel(name = "检验阶代码")
        @ApiModelProperty(value = "检验阶代码")
        private String otsCode;
        @Excel(name = "检测项名称")
        @ApiModelProperty(value = "检测项名称")
        private String name;
        @Excel(name = "值域(0:单值 1:范围值 2:状态值)")
        @ApiModelProperty(value = "值域(0:单值 1:范围值 2:状态值)")
        private Long ranges;
        @Excel(name = "单位")
        @ApiModelProperty(value = "单位")
        private String unit;
        @Excel(name = "检测方法")
        @ApiModelProperty(value = "检测方法")
        private String method;
        @Excel(name = "检测工序")
        @ApiModelProperty(value = "检测工序")
        private Long groups;
        @Excel(name = "下限值")
        @ApiModelProperty(value = "下限值")
        private String lowerLimit;
        @Excel(name = "上限值")
        @ApiModelProperty(value = "上限值")
        private String upperLimit;
        @Excel(name = "说明")
        @ApiModelProperty(value = "说明")
        private String remark;
        @Excel(name = "显示顺序")
        @ApiModelProperty(value = "显示顺序")
        private Integer sort;
        @Excel(name = "机构代码")
        @ApiModelProperty(value = "机构代码")
        private String depaCode;
        @Excel(name = "部门名称")
        @ApiModelProperty(value = "部门名称")
        private String depaName;
}
