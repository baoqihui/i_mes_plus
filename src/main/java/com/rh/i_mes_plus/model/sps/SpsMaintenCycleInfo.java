package com.rh.i_mes_plus.model.sps;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 保养周期信息表
 *
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sps_mainten_cycle_info")
public class SpsMaintenCycleInfo extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "保养周期代码")
        @ApiModelProperty(value = "保养周期代码")
        private String cycleCode;
        @Excel(name = "保养周期名称")
        @ApiModelProperty(value = "保养周期名称")
        private String cycleName;
        @Excel(name = "保养间隔天数")
        @ApiModelProperty(value = "保养间隔天数")
        private Integer days;
}
