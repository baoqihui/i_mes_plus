package com.rh.i_mes_plus.model.sps;

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
 * @date 2021-05-21 15:02:34
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sps_feeder_info")
public class SpsFeederInfo extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "规格代码")
        @ApiModelProperty(value = "规格代码")
        private String specCode;
        @Excel(name = "料枪SN")
        @ApiModelProperty(value = "料枪SN")
        private String feederSn;
        @Excel(name = "右通道SN")
        @ApiModelProperty(value = "右通道SN")
        private String rightChannelSn;
        @Excel(name = "左通道SN")
        @ApiModelProperty(value = "左通道SN")
        private String leftChannelSn;
        @Excel(name = "中通道SN")
        @ApiModelProperty(value = "中通道SN")
        private String inChannelSn;
        @Excel(name = "保养类型")
        @ApiModelProperty(value = "保养类型")
        private String maintenCycleCode;
        @Excel(name = "提醒周期")
        @ApiModelProperty(value = "提醒周期")
        private Integer maintenCycleDays;
        @Excel(name = "线别代码")
        @ApiModelProperty(value = "线别代码")
        private String lineCode;
        @Excel(name = "正反面")
        @ApiModelProperty(value = "正反面")
        private String scFlag;
        @Excel(name = "站位")
        @ApiModelProperty(value = "站位")
        private String position;
        @Excel(name = "状态")
        @ApiModelProperty(value = "状态")
        private Integer state;
}
