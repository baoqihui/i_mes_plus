package com.rh.i_mes_plus.model.ums;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工单阶别(0-SMT,1-WAVE,2-Hand Soldering,3-Assembly,4-Others)
 *
 * @author hbq
 * @date 2021-05-31 10:07:36
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_mo_step")
public class UmsMoStep extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "工单阶别代码")
        @ApiModelProperty(value = "工单阶别代码")
        private String msCode;
        @Excel(name = "工单阶别名称")
        @ApiModelProperty(value = "工单阶别名称")
        private String msName;
        @Excel(name = "阶别类型（1，数据统计阶 2，制令单生产阶)")
        @ApiModelProperty(value = "阶别类型（1，数据统计阶 2，制令单生产阶)")
        private Integer msType;
        @Excel(name = "计算对象(0:不计算 1:元器件数 2:焊点数 3:PCS)")
        @ApiModelProperty(value = "计算对象(0:不计算 1:元器件数 2:焊点数 3:PCS)")
        private Integer msCalculateObject;
        @Excel(name = "贴片阶标识(Y/N)")
        @ApiModelProperty(value = "贴片阶标识(Y/N)")
        private String msSmtFlag;
        @Excel(name = "检验阶")
        @ApiModelProperty(value = "检验阶")
        private String msTestStep;
        @Excel(name = "电芯阶标识(Y/N)")
        @ApiModelProperty(value = "电芯阶标识(Y/N)")
        private String msBatteriesFlag;
        @Excel(name = "制令单误差量")
        @ApiModelProperty(value = "制令单误差量")
        private Long msMoErrorAmount;
        @Excel(name = "前置阶别代码")
        @ApiModelProperty(value = "前置阶别代码")
        private String previousStep;
        @Excel(name = "工单投入阶(Y/N)")
        @ApiModelProperty(value = "工单投入阶(Y/N)")
        private String inputFlag;
        @Excel(name = "工单产出阶(Y/N)")
        @ApiModelProperty(value = "工单产出阶(Y/N)")
        private String outputFlag;
}
