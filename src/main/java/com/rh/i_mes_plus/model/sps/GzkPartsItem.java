package com.rh.i_mes_plus.model.sps;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import com.rh.i_mes_plus.common.model.SuperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 工装备品物料
 *
 * @author hbq
 * @date 2021-06-23 15:54:13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("gzk_parts_item")
public class GzkPartsItem extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "物料代码")
        @ApiModelProperty(value = "物料代码")
        private String itemCode;
        @Excel(name = "物料名称")
        @ApiModelProperty(value = "物料名称")
        private String itemName;
        @Excel(name = "物料描述")
        @ApiModelProperty(value = "物料描述")
        private String itemDesc;
        @Excel(name = "小类代码")
        @ApiModelProperty(value = "小类代码")
        private String itemTypeCode;
        @Excel(name = "最小库存")
        @ApiModelProperty(value = "最小库存")
        private String minQty;
        @Excel(name = "是否预警")
        @ApiModelProperty(value = "是否预警")
        private Integer isWarn;

}
