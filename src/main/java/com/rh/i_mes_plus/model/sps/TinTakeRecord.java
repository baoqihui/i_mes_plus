package com.rh.i_mes_plus.model.sps;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import com.rh.i_mes_plus.common.model.SuperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 
 *
 * @author hbq
 * @date 2021-07-08 19:48:01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tin_take_record")
public class TinTakeRecord extends SuperEntity {
    private static final long serialVersionUID=1L;
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
        @Excel(name = "制令单号")
        @ApiModelProperty(value = "制令单号")
        private String moNo;
        @Excel(name = "料号")
        @ApiModelProperty(value = "料号")
        private String itemCode;
}
