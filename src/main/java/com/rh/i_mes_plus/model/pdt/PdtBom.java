package com.rh.i_mes_plus.model.pdt;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * bom表
 *
 * @author hbq
 * @date 2021-05-25 13:18:22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pdt_bom")
public class PdtBom extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "物料代码")
        @ApiModelProperty(value = "物料代码")
        private String itemCode;
        @Excel(name = "物料名称")
        @ApiModelProperty(value = "物料名称")
        private String itemName;
        @Excel(name = "物料描述")
        @ApiModelProperty(value = "物料描述")
        private String itemSpec;
        @Excel(name = "版本")
        @ApiModelProperty(value = "版本")
        private String ver;
        @Excel(name = "客户编号")
        @ApiModelProperty(value = "客户编号")
        private String custCode;
        @Excel(name = "项目号")
        @ApiModelProperty(value = "项目号")
        private String projectCode;
        @Excel(name = "是否默认(0,不默认 1,默认)")
        @ApiModelProperty(value = "是否默认(0,不默认 1,默认)")
        private Integer isDefault;
        @Excel(name = "关联ecn（','分割）")
        @ApiModelProperty(value = "关联ecn（','分割）")
        private String ecns;
        @Excel(name = "备注")
        @ApiModelProperty(value = "备注")
        private String remark;
        @Excel(name = "是否生效（0，无效 1，有效）")
        @ApiModelProperty(value = "是否生效（0，无效 1，有效）")
        private Integer isValue;
        @Excel(name = "阶段（1，试产 2，量产）")
        @ApiModelProperty(value = "阶段（1，试产 2，量产）")
        private Integer stage;
        @Excel(name = "创建人")
        @ApiModelProperty(value = "创建人")
        private String createName;
        @Excel(name = "编辑人",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "编辑人")
        private String updateName;

}
