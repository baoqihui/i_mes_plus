package com.rh.i_mes_plus.model.pdt;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 料站表详情
 *
 * @author hbq
 * @date 2021-05-24 10:17:57
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pdt_feeding_station_detail")
public class PdtFeedingStationDetail extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "料站表sn")
        @ApiModelProperty(value = "料站表sn")
        private String fsSn;
        @Excel(name = "上料点sn")
        @ApiModelProperty(value = "上料点sn")
        private String feedingPointSn;
        @Excel(name = "料号")
        @ApiModelProperty(value = "料号")
        private String itemCode;
        @Excel(name = "物料名称")
        @ApiModelProperty(value = "物料名称")
        private String itemName;
        @Excel(name = "物料规格")
        @ApiModelProperty(value = "物料规格")
        private String itemSpec;
        @Excel(name = "送料器类型（1：料盘， 2： Tray）")
        @ApiModelProperty(value = "送料器类型（1：料盘， 2： Tray）")
        private String itemType;
        @Excel(name = "pcb标识（Y/N）")
        @ApiModelProperty(value = "pcb标识（Y/N）")
        private String pcbFlag;
        @Excel(name = "零件位置")
        @ApiModelProperty(value = "零件位置")
        private String position;
        @Excel(name = "用量")
        @ApiModelProperty(value = "用量")
        private Integer qty;
        @Excel(name = "步距")
        @ApiModelProperty(value = "步距")
        private String step;
        @Excel(name = "角度")
        @ApiModelProperty(value = "角度")
        private Integer section;
        @Excel(name = "通道")
        @ApiModelProperty(value = "通道")
        private String channel;
        @Excel(name = "备注")
        @ApiModelProperty(value = "备注")
        private String remark;
}
