package com.rh.i_mes_plus.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
public class TinTakeRecordVO {
    @Excel(name = "id")
    @ApiModelProperty(value = "id")
    private Long id;
    @Excel(name = "红锡膏SN")
    @ApiModelProperty(value = "红锡膏SN")
    private String tinSn;
    @Excel(name = "回温时间",format="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "回温时间")
    private Date takeStartTime;
    @Excel(name = "回温人")
    @ApiModelProperty(value = "回温人")
    private String takeStartName;
    @Excel(name = "结束时间",format="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束时间")
    private Date takeEndTime;
    @Excel(name = "料号")
    @ApiModelProperty(value = "料号")
    private String itemCode;
    @Excel(name = "预回温结束时间")
    @ApiModelProperty(value = "预回温结束时间")
    private Date preEndTime;
    @Excel(name = "常温报废时间")
    @ApiModelProperty(value = "常温报废时间")
    private Date normalScrapTime;
    @Excel(name = "物料名称")
    @ApiModelProperty(value = "物料名称")
    private String itemName;
    @Excel(name = "物料规格")
    @ApiModelProperty(value = "物料规格")
    private String itemSpec;
    @Excel(name = "回温时间（小时）")
    @ApiModelProperty(value = "回温时间（小时）")
    private Integer backTime;
    @Excel(name = "状态（1，回温中 2，回温OK，可领用 3，回温OK，可领用，还差一小时报废 4，常温超时）")
    @ApiModelProperty(value = "状态（1，回温中 2，回温OK，可领用 3，回温OK，可领用，还差一小时报废 4，常温超时）")
    private Integer state;
    @Excel(name = "回温状态（0，回温中 1，回温完成）")
    @ApiModelProperty(value = "回温状态（0，回温中 1，回温完成）")
    private Integer status;
}
