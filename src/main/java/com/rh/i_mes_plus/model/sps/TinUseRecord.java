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
 * 红锡膏领用记录
 *
 * @author hbq
 * @date 2021-07-08 19:48:01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tin_use_record")
public class TinUseRecord extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "回温主键")
        @ApiModelProperty(value = "回温主键")
        private Long takeId;
        @Excel(name = "料号")
        @ApiModelProperty(value = "料号")
        private String itemCode;
        @Excel(name = "红锡膏SN")
        @ApiModelProperty(value = "红锡膏SN")
        private String tinSn;
        @Excel(name = "领用时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "领用时间")
        private Date useTime;
        @Excel(name = "领用人")
        @ApiModelProperty(value = "领用人")
        private String useName;
        @Excel(name = "班别代码")
        @ApiModelProperty(value = "班别代码")
        private String team;
        @Excel(name = "线别")
        @ApiModelProperty(value = "线别")
        private String lineCode;
        @Excel(name = "制令单号")
        @ApiModelProperty(value = "制令单号")
        private String moNo;
        @Excel(name = "开罐时间（非原罐该时间为最后一次的回温时间）",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "开罐时间（非原罐该时间为最后一次的回温时间）")
        private Date openTime;
        @Excel(name = "开罐人")
        @ApiModelProperty(value = "开罐人")
        private String openName;
        @Excel(name = "区域SN")
        @ApiModelProperty(value = "区域SN")
        private String areaSn;
        @Excel(name = "操作员")
        @ApiModelProperty(value = "操作员")
        private String operator;
        @Excel(name = "供应商代码")
        @ApiModelProperty(value = "供应商代码")
        private String supplierCode;
        @Excel(name = "入库时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "入库时间")
        private Date receiveTime;
        @Excel(name = "过期时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "过期时间")
        private Date expireTime;
        @Excel(name = "生产批次号")
        @ApiModelProperty(value = "生产批次号")
        private String lotNo;
        @Excel(name = "发料人")
        @ApiModelProperty(value = "发料人")
        private String outName;
        @Excel(name = "上锡膏时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "上锡膏时间")
        private Date useingTime;
        @Excel(name = "上锡膏人")
        @ApiModelProperty(value = "上锡膏人")
        private String useingMan;
        @Excel(name = "正在使用标志( 0未使用1使用中2退库)")
        @ApiModelProperty(value = "正在使用标志( 0未使用1使用中2退库)")
        private Integer useingFlag;
}
