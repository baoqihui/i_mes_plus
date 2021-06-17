package com.rh.i_mes_plus.model.sps;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 设备保养维修管理
 *
 * @author hbq
 * @date 2021-06-08 17:55:16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sps_feeder_mainten_log")
public class SpsFeederMaintenLog extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "料枪SN")
        @ApiModelProperty(value = "料枪SN")
        private String feederSn;
        @Excel(name = "类型（1，保养 2，维修）")
        @ApiModelProperty(value = "类型（1，保养 2，维修）")
        private Integer type;
        @Excel(name = "保养周期代码")
        @ApiModelProperty(value = "保养周期代码")
        private String cycleCode;
        @Excel(name = "保养内容名称集合")
        @ApiModelProperty(value = "保养内容名称集合")
        private String commentNames;
        @Excel(name = "保养内容代码集合")
        @ApiModelProperty(value = "保养内容代码集合")
        private String commentCodes;
        @Excel(name = "不良现象")
        @ApiModelProperty(value = "不良现象")
        private String badPhenomenon;
        @Excel(name = "维修内容")
        @ApiModelProperty(value = "维修内容")
        private String maintenanceContent;
        @Excel(name = "保养时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "保养时间")
        private Date maintenTime;
        @Excel(name = "保养人")
        @ApiModelProperty(value = "保养人")
        private String maintener;
        @Excel(name = "备注")
        @ApiModelProperty(value = "备注")
        private String remark;
}
