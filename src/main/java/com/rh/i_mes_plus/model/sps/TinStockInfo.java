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
 * 锡膏红胶库存表
 *
 * @author hbq
 * @date 2021-07-08 19:48:01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tin_stock_info")
public class TinStockInfo extends SuperEntity {
    private static final long serialVersionUID=1L;
        @Excel(name = "入库单号")
        @ApiModelProperty(value = "入库单号")
        private String docNo;
        @Excel(name = "红锡膏SN")
        @ApiModelProperty(value = "红锡膏SN")
        private String tinSn;
        @Excel(name = "红锡膏料号")
        @ApiModelProperty(value = "红锡膏料号")
        private String itemCode;
        @Excel(name = "供应商代码")
        @ApiModelProperty(value = "供应商代码")
        private String supplierCode;
        @Excel(name = "入库时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "入库时间")
        private Date receiveTime;
        @Excel(name = "入库人")
        @ApiModelProperty(value = "入库人")
        private String receiveName;
        @Excel(name = "生产日期",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "生产日期")
        private Date manufactureDate;
        @Excel(name = "保质期（月）")
        @ApiModelProperty(value = "保质期（月）")
        private Integer shelfLife;
        @Excel(name = "过期时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "过期时间")
        private Date expireTime;
        @Excel(name = "回温次数")
        @ApiModelProperty(value = "回温次数")
        private Integer useTimes;
        @Excel(name = "生产批次号")
        @ApiModelProperty(value = "生产批次号")
        private String lotNo;
        @Excel(name = "状态(0在库 1回温 )")
        @ApiModelProperty(value = "状态(0在库 1回温 )")
        private Integer status;
        @Excel(name = "区域SN")
        @ApiModelProperty(value = "区域SN")
        private String areaSn;
        @Excel(name = "开罐标识")
        @ApiModelProperty(value = "开罐标识")
        private Integer isOpen;
        @Excel(name = "盘点标识")
        @ApiModelProperty(value = "盘点标识")
        private Integer isCheck;
        @Excel(name = "有效标识")
        @ApiModelProperty(value = "有效标识")
        private Integer isValue;
}
