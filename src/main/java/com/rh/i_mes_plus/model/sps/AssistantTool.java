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
 * 辅料信息
 *
 * @author hbq
 * @date 2021-07-08 19:48:01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("assistant_tool")
public class AssistantTool extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "料号")
        @ApiModelProperty(value = "料号")
        private String itemCode;
        @Excel(name = "物料名称")
        @ApiModelProperty(value = "物料名称")
        private String itemName;
        @Excel(name = "物料描述")
        @ApiModelProperty(value = "物料描述")
        private String itemSpec;
        @Excel(name = "类型")
        @ApiModelProperty(value = "类型")
        private String typeCode;
        @Excel(name = "安全库存量")
        @ApiModelProperty(value = "安全库存量")
        private Long leastQty;
        @Excel(name = "库存")
        @ApiModelProperty(value = "库存")
        private Long qty;
        @Excel(name = "锡膏开罐报废时间(分钟)")
        @ApiModelProperty(value = "锡膏开罐报废时间(分钟)")
        private Long openDiscardTime;
        @Excel(name = "锡膏未开罐报废时间(分钟)")
        @ApiModelProperty(value = "锡膏未开罐报废时间(分钟)")
        private Long discardTime;
        @Excel(name = "回温时间(分钟)")
        @ApiModelProperty(value = "回温时间(分钟)")
        private Integer backTime;
        @Excel(name = "上锡膏到回焊炉前时间(分钟)")
        @ApiModelProperty(value = "上锡膏到回焊炉前时间(分钟)")
        private Long duringTime;
        @Excel(name = "ROSH标识")
        @ApiModelProperty(value = "ROSH标识")
        private Integer leadFlag;
        @Excel(name = "回温次数")
        @ApiModelProperty(value = "回温次数")
        private Integer useTimes;
}
