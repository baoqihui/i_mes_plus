package com.rh.i_mes_plus.model.sps;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import com.rh.i_mes_plus.common.model.SuperEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 锡膏操作日志
 *
 * @author hbq
 * @date 2021-07-15 10:18:07
 */
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tin_log")
public class TinLog extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "锡膏SN")
        @ApiModelProperty(value = "锡膏SN")
        private String tinSn;
        @Excel(name = "料号")
        @ApiModelProperty(value = "料号")
        private String itemCode;
        @Excel(name = "生产日期",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "生产日期")
        private Date manufactureDate;
        @Excel(name = "生产批次号")
        @ApiModelProperty(value = "生产批次号")
        private String lotNo;
        @Excel(name = "操作内容")
        @ApiModelProperty(value = "操作内容")
        private String content;
}
