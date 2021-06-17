package com.rh.i_mes_plus.model.sps;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 钢网详情信息表
 *
 * @author hbq
 * @date 2021-06-03 18:19:02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("mfg_stencils_detail_info")
public class MfgStencilsDetailInfo extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "钢网号")
        @ApiModelProperty(value = "钢网号")
        private String stencilNo;
        @Excel(name = "治具大类代码")
        @ApiModelProperty(value = "治具大类代码")
        private String typeCode;
        @Excel(name = "小类别代码")
        @ApiModelProperty(value = "小类别代码")
        private String itemTypeCode;
        @Excel(name = "适用机型")
        @ApiModelProperty(value = "适用机型")
        private String modelCode;
        @Excel(name = "物料代码")
        @ApiModelProperty(value = "物料代码")
        private String itemCode;
        @Excel(name = "物料名称")
        @ApiModelProperty(value = "物料名称")
        private String itemName;
        @Excel(name = "物料描述")
        @ApiModelProperty(value = "物料描述")
        private String itemSpec;
        @Excel(name = "拼板数")
        @ApiModelProperty(value = "拼板数")
        private Integer pcbCount;
        @Excel(name = "最大使用次数")
        @ApiModelProperty(value = "最大使用次数")
        private Long maxTimes;
        @Excel(name = "使用次数")
        @ApiModelProperty(value = "使用次数")
        private Long usedTimes;
        @Excel(name = "是否有框")
        @ApiModelProperty(value = "是否有框")
        private Integer hasFrame;
        @Excel(name = "来源类型（1，自费 2，客供）")
        @ApiModelProperty(value = "来源类型（1，自费 2，客供）")
        private Integer itemSource;
        @Excel(name = "客户代码")
        @ApiModelProperty(value = "客户代码")
        private String custCode;
        @Excel(name = "供应商代码")
        @ApiModelProperty(value = "供应商代码")
        private String supplierCode;
        @Excel(name = "储位")
        @ApiModelProperty(value = "储位")
        private String pos;
        @Excel(name = "治具产权")
        @ApiModelProperty(value = "治具产权")
        private String ownership;
        @Excel(name = "左上")
        @ApiModelProperty(value = "左上")
        private Long leftUpper;
        @Excel(name = "左下")
        @ApiModelProperty(value = "左下")
        private Long leftLower;
        @Excel(name = "右上")
        @ApiModelProperty(value = "右上")
        private Long rightUpper;
        @Excel(name = "右下")
        @ApiModelProperty(value = "右下")
        private Long rightLower;
        @Excel(name = "中间")
        @ApiModelProperty(value = "中间")
        private Long centre;
        @Excel(name = "状态(1.在库 2.借出 3.报废 4.保养)")
        @ApiModelProperty(value = "状态(1.在库 2.借出 3.报废 4.保养)")
        private String state;
        @Excel(name = "入库时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "入库时间")
        private Date inTime;
        @Excel(name = "入库人")
        @ApiModelProperty(value = "入库人")
        private String inName;
        @Excel(name = "保养提醒")
        @ApiModelProperty(value = "保养提醒")
        private Integer maintenWarn;
        @Excel(name = "外观检测(0，ng 1，ok)")
        @ApiModelProperty(value = "外观检测(0，ng 1，ok)")
        private Integer appearanceInspection;
        @Excel(name = "正反面")
        @ApiModelProperty(value = "正反面")
        private String scFlag;
}
