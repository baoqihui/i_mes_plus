package com.rh.i_mes_plus.model.pdt;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import com.rh.i_mes_plus.common.model.SuperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 维修方法
 *
 * @author hbq
 * @date 2021-07-05 10:11:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pdt_fault_repair_moth")
public class PdtFaultRepairMoth extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "维修方法代码")
        @ApiModelProperty(value = "维修方法代码")
        private String repairCode;
        @Excel(name = "维修方法描述")
        @ApiModelProperty(value = "维修方法描述")
        private String repairName;
}
