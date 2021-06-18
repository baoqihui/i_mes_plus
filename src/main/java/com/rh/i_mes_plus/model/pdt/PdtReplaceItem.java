package com.rh.i_mes_plus.model.pdt;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 替代料
 *
 * @author hbq
 * @date 2021-03-30 13:20:00
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pdt_replace_item")
public class PdtReplaceItem extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "替代组编号")
        @ApiModelProperty(value = "替代组编号")
        private String replaceGroup;
        @Excel(name = "适用机种")
        @ApiModelProperty(value = "适用机种")
        private String modelCode;
        @Excel(name = "物料编号")
        @ApiModelProperty(value = "物料编号")
        private String itemCode;
        @Excel(name = "物料名称")
        @ApiModelProperty(value = "物料名称")
        private String itemName;
        @Excel(name = "优先级排序")
        @ApiModelProperty(value = "优先级排序")
        private Long sort;
        @Excel(name = "是否启用",replace ={"否_false","是_true"})
        @ApiModelProperty(value = "是否启用")
        private Boolean isOpen;
        @Excel(name = "创建人")
        @ApiModelProperty(value = "创建人")
        private String createName;
        @Excel(name = "备注")
        @ApiModelProperty(value = "备注")
        private String remark1;
        @Excel(name = "类型")
        @ApiModelProperty(value = "类型")
        private String remark2;
}
