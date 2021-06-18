package com.rh.i_mes_plus.model.pdt;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 料站表主表
 *
 * @author hbq
 * @date 2021-05-24 10:17:57
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pdt_feeding_station")
public class PdtFeedingStation extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "料站表sn")
        @ApiModelProperty(value = "料站表sn")
        private String fsSn;
        @Excel(name = "料站表名称")
        @ApiModelProperty(value = "料站表名称")
        private String fsName;
        @Excel(name = "机种代码")
        @ApiModelProperty(value = "机种代码")
        private String modelCode;
        @Excel(name = "机种名称")
        @ApiModelProperty(value = "机种名称")
        private String modelName;
        @Excel(name = "PCB料号")
        @ApiModelProperty(value = "PCB料号")
        private String pcbItemCode;
        @Excel(name = "线别代码")
        @ApiModelProperty(value = "线别代码")
        private String lineCode;
        @Excel(name = "工作中心")
        @ApiModelProperty(value = "工作中心")
        private String wcCode;
        @Excel(name = "机种规格")
        @ApiModelProperty(value = "机种规格")
        private String modelSpec;
        @Excel(name = "贴片类型（0：贴片 1：立插 2：卧插）")
        @ApiModelProperty(value = "贴片类型（0：贴片 1：立插 2：卧插）")
        private Integer pasterType;
        @Excel(name = "贴片点数（考虑删除）")
        @ApiModelProperty(value = "贴片点数（考虑删除）")
        private Integer pasterQty;
        @Excel(name = "贴片速率")
        @ApiModelProperty(value = "贴片速率")
        private Integer pasterSpeed;
        @Excel(name = "加工面（正面：T，反面：B）")
        @ApiModelProperty(value = "加工面（正面：T，反面：B）")
        private String pcbType;
        @Excel(name = "生效标识")
        @ApiModelProperty(value = "生效标识")
        private Integer isValue;
        @Excel(name = "变更说明")
        @ApiModelProperty(value = "变更说明")
        private String remark;
}
