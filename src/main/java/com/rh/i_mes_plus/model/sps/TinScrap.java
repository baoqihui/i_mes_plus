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
 * 红锡膏报废
 *
 * @author hbq
 * @date 2021-07-08 19:48:01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tin_scrap")
public class TinScrap extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "红锡膏SN")
        @ApiModelProperty(value = "红锡膏SN")
        private String tinSn;
        @Excel(name = "报废时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "报废时间")
        private Date scrapTime;
        @Excel(name = "报废人")
        @ApiModelProperty(value = "报废人")
        private String scrapName;
        @Excel(name = "报废原因")
        @ApiModelProperty(value = "报废原因")
        private String scrapReason;
        @Excel(name = "料号")
        @ApiModelProperty(value = "料号")
        private String itemCode;
        @Excel(name = "供应商代码")
        @ApiModelProperty(value = "供应商代码")
        private String supplierCode;
        @Excel(name = "入库时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "入库时间")
        private Date receiveTime;
        @Excel(name = "入库单据号")
        @ApiModelProperty(value = "入库单据号")
        private String receiveDocNo;
        @Excel(name = "入库人")
        @ApiModelProperty(value = "入库人")
        private String receiveName;
        @Excel(name = "过期时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "过期时间")
        private Date expireTime;
        @Excel(name = "区域SN")
        @ApiModelProperty(value = "区域SN")
        private String areaSn;
}
