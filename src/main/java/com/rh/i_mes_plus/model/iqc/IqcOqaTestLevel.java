package com.rh.i_mes_plus.model.iqc;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 机种抽样方案
 *
 * @author hqb
 * @date 2020-10-16 11:39:52
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("iqc_oqa_test_level")
public class IqcOqaTestLevel extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "机种")
        @ApiModelProperty(value = "机种")
        private String itemCode;
        @Excel(name = "检验类型ID")
        @ApiModelProperty(value = "检验类型ID")
        private Long tiyId;
        @Excel(name = "检验水平ID")
        @ApiModelProperty(value = "检验水平ID")
        private Long otgId;
        @Excel(name = "抽样方案")
        @ApiModelProperty(value = "抽样方案")
        private Long ottId;
        @Excel(name = "AQL值")
        @ApiModelProperty(value = "AQL值")
        private Double aql;
        @Excel(name = "检验阶代码")
        @ApiModelProperty(value = "检验阶代码")
        private String otsCode;
        @Excel(name = "缺陷等级代码")
        @ApiModelProperty(value = "缺陷等级代码")
        private String odlCode;
        @Excel(name = "默认标准(1/0)")
        @ApiModelProperty(value = "默认标准(1/0)")
        private Boolean isDef;
        @Excel(name = "部门代码")
        @ApiModelProperty(value = "部门代码")
        private String depaCode;
        @Excel(name = "部门名称")
        @ApiModelProperty(value = "部门名称")
        private String depaName;
}
