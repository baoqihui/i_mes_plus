package com.rh.i_mes_plus.model.ums;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 备品类别信息表
 *
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_spares_item_type_info")
public class UmsSparesItemTypeInfo extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "大类代码")
        @ApiModelProperty(value = "大类代码")
        private String typeCode;
        @Excel(name = "类别代码")
        @ApiModelProperty(value = "类别代码")
        private String itemTypeCode;
        @Excel(name = "类别名称")
        @ApiModelProperty(value = "类别名称")
        private String itemTypeName;
        @Excel(name = "物料代码")
        @ApiModelProperty(value = "物料代码")
        private String itemCode;
        @Excel(name = "类别描述")
        @ApiModelProperty(value = "类别描述")
        private String itemTypeDesc;
        @Excel(name = "最小库存")
        @ApiModelProperty(value = "最小库存")
        private String minQty;
}
