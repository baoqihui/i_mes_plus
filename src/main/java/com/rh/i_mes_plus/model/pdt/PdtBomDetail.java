package com.rh.i_mes_plus.model.pdt;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 *
 * @author hbq
 * @date 2021-05-25 13:18:22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pdt_bom_detail")
public class PdtBomDetail extends SuperEntity {
    private static final long serialVersionUID=1L;
        @TableField(exist = false)
        private String SN;
        @Excel(name = "")
        @ApiModelProperty(value = "")
        private Long parentId;
        @Excel(name = "阶别")
        @ApiModelProperty(value = "阶别")
        private Integer assyLevel;
        @Excel(name = "料号")
        @ApiModelProperty(value = "料号")
        private String itemCode;
        @Excel(name = "物料名称")
        @ApiModelProperty(value = "物料名称")
        private String itemName;
        @Excel(name = "正面点位")
        @ApiModelProperty(value = "正面点位")
        private String frontPointLocation;
        @Excel(name = "正面用量")
        @ApiModelProperty(value = "正面用量")
        private Long frontUseNum;
        @Excel(name = "反面点位")
        @ApiModelProperty(value = "反面点位")
        private String reversePointLocation;
        @Excel(name = "Bom层")
        @ApiModelProperty(value = "Bom层")
        private Long reverseUseNum;
        @Excel(name = "单位")
        @ApiModelProperty(value = "单位")
        private String unit;
        @Excel(name = "备注1")
        @ApiModelProperty(value = "备注1")
        private String remark1;
        @Excel(name = "备注2")
        @ApiModelProperty(value = "备注2")
        private String remark2;
        @Excel(name = "备注3")
        @ApiModelProperty(value = "备注3")
        private String remark3;
        @Excel(name = "描述1")
        @ApiModelProperty(value = "描述1")
        private String desc1;
        @Excel(name = "描述2")
        @ApiModelProperty(value = "描述2")
        private String desc2;
        @Excel(name = "描述3")
        @ApiModelProperty(value = "描述3")
        private String desc3;
        @Excel(name = "工序")
        @ApiModelProperty(value = "工序")
        private String processCode;
        @Excel(name = "Bom层")
        @ApiModelProperty(value = "Bom层")
        private String bomStratum;
        @Excel(name = "pcb标识")
        @ApiModelProperty(value = "pcb标识")
        private Integer pcbFlag;
        @Excel(name = "是否生效（0，无效 1，有效）")
        @ApiModelProperty(value = "是否生效（0，无效 1，有效）")
        private Integer isValue;
}
