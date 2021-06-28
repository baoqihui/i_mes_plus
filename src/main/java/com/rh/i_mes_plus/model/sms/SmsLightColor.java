package com.rh.i_mes_plus.model.sms;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import com.rh.i_mes_plus.common.model.SuperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 仓库灯资源占用
 *
 * @author hbq
 * @date 2021-06-24 15:19:55
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_light_color")
public class SmsLightColor extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "颜色码")
        @ApiModelProperty(value = "颜色码")
        private String colorCode;
        @Excel(name = "颜色名")
        @ApiModelProperty(value = "颜色名")
        private String colorName;
        @Excel(name = "排序")
        @ApiModelProperty(value = "排序")
        private Integer sort;
        @Excel(name = "状态（0，未占用 1，已占用）")
        @ApiModelProperty(value = "状态（0，未占用 1，已占用）")
        private Integer state;
}
