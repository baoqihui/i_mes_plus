package com.rh.i_mes_plus.model.pdt;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * bom详情替代料信息
 *
 * @author hbq
 * @date 2021-06-09 20:13:20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pdt_bom_detail_replace_item")
public class PdtBomDetailReplaceItem extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "bom明细id")
        @ApiModelProperty(value = "bom明细id")
        private Long pbdId;
        @Excel(name = "物料代码")
        @ApiModelProperty(value = "物料代码")
        private String itemCode;
        @Excel(name = "物料名称")
        @ApiModelProperty(value = "物料名称")
        private String itemName;
}
