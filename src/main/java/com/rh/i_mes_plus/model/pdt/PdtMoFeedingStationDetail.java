package com.rh.i_mes_plus.model.pdt;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import com.rh.i_mes_plus.common.model.SuperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 *
 * @author hbq
 * @date 2021-07-06 14:21:53
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pdt_mo_feeding_station_detail")
public class PdtMoFeedingStationDetail extends SuperEntity {
    private static final long serialVersionUID=1L;

    @Excel(name = "制令单号")
    @ApiModelProperty(value = "制令单号")
    private String moNo;
    @Excel(name = "机种")
    @ApiModelProperty(value = "机种")
    private String modelCode;
    @Excel(name = "料号")
    @ApiModelProperty(value = "料号")
    private String itemCode;
    @Excel(name = "上料点sn")
    @ApiModelProperty(value = "上料点sn")
    private String feedingPointSn;
    @Excel(name = "点位")
    @ApiModelProperty(value = "点位")
    private String position;
    @Excel(name = "点数")
    @ApiModelProperty(value = "点数")
    private Integer qty;
    @Excel(name = "贴片类型（0：贴片 1：立插 2：卧插）")
    @ApiModelProperty(value = "贴片类型（0：贴片 1：立插 2：卧插）")
    private Integer pasterType;
    @Excel(name = "替代料")
    @ApiModelProperty(value = "替代料")
    private String replaceItemCode;
    @Excel(name = "料盘条码")
    @ApiModelProperty(value = "料盘条码")
    private String itemSn;
    @Excel(name = "通道(l：表示左 r：表示右 m：表中间)")
    @ApiModelProperty(value = "通道(l：表示左 r：表示右 m：表中间)")
    private String channel;
    @Excel(name = "上料状态(0，未上料 1，已上料)")
    @ApiModelProperty(value = "上料状态(0，未上料 1，已上料)")
    private Integer status;
}
