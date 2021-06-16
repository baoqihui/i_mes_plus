package com.rh.i_mes_plus.model.sms;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 调拨单物料明细表
 *
 * @author hbq
 * @date 2020-12-11 13:39:17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_wms_move_doc_detail")
public class SmsWmsMoveDocDetail extends SuperEntity {
    private static final long serialVersionUID=1L;
    
    @Excel(name = "调拨单号")
    @ApiModelProperty(value = "调拨单号")
    private String moveNo;
    @Excel(name = "物料编码")
    @ApiModelProperty(value = "物料编码")
    private String itemCode;
    @Excel(name = "物料名称")
    @ApiModelProperty(value = "物料名称")
    private String itemName;
    @Excel(name = "物料描述")
    @ApiModelProperty(value = "物料描述")
    private String itemSpec;
    @Excel(name = "是否烧录物料")
    @ApiModelProperty(value = "是否烧录物料")
    private String isBurning;
    @Excel(name = "申请数量")
    @ApiModelProperty(value = "申请数量")
    private Long planAmount;
    @Excel(name = "已调拨量")
    @ApiModelProperty(value = "已调拨量")
    private Long realAmount;
    @Excel(name = "调入仓库sn")
    @ApiModelProperty(value = "调入仓库sn")
    private String inWhCode;
    @Excel(name = "调出仓库sn")
    @ApiModelProperty(value = "调出仓库sn")
    private String outWhCode;
    @Excel(name = "计量单位id")
    @ApiModelProperty(value = "计量单位id")
    private String cuDid;
    @Excel(name = "erp调拨单明细序号")
    @ApiModelProperty(value = "erp调拨单明细序号")
    private String erpMoveId;
    @Excel(name = "erp调拨单别")
    @ApiModelProperty(value = "erp调拨单别")
    private String erpMoveType;
    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String remark;
}
