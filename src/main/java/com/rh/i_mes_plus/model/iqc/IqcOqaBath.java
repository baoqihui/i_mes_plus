package com.rh.i_mes_plus.model.iqc;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 检验单明细
 *
 * @author hbq
 * @date 2020-10-21 14:29:19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("iqc_oqa_bath")
public class IqcOqaBath extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "抽检单号")
        @ApiModelProperty(value = "抽检单号")
        private String oqcNo;
        @Excel(name = "制令号")
        @ApiModelProperty(value = "制令号")
        private String moNumber;
        @Excel(name = "机种代码")
        @ApiModelProperty(value = "机种代码")
        private String itemCode;
        @Excel(name = "抽样批次号")
        @ApiModelProperty(value = "抽样批次号")
        private String lotNumber;
        @Excel(name = "机器序列号(条码)")
        @ApiModelProperty(value = "机器序列号(条码)")
        private String serialNumber;
        @Excel(name = "抽验结果 1、OK 2、NG 3、未检 4、在检")
        @ApiModelProperty(value = "抽验结果 1、OK 2、NG 3、未检 4、在检")
        private String osResult;
        @Excel(name = "数量")
        @ApiModelProperty(value = "数量")
        private Long osAmount;
        @Excel(name = "损耗数量")
        @ApiModelProperty(value = "损耗数量")
        private Long osQtyLoss;
        @Excel(name = "样本标识(0/1)")
        @ApiModelProperty(value = "样本标识(0/1)")
        private Boolean isOsSample;
        @Excel(name = "重工标识(0/1)")
        @ApiModelProperty(value = "重工标识(0/1)")
        private Boolean isRework;
        @Excel(name = "报工标识(0/1)")
        @ApiModelProperty(value = "报工标识(0/1)")
        private Boolean isErpOk;
        @Excel(name = "最后标识(0/1)")
        @ApiModelProperty(value = "最后标识(0/1)")
        private Boolean isLast;
        @Excel(name = "机构代码")
        @ApiModelProperty(value = "机构代码")
        private String depaCode;
        @Excel(name = "部门名称")
        @ApiModelProperty(value = "部门名称")
        private String depaName;
}
