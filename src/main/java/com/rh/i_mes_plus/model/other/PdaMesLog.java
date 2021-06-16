package com.rh.i_mes_plus.model.other;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * pda与mes操作日志
 *
 * @author hbq
 * @date 2021-03-17 14:33:43
 */
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pda_mes_log")
public class PdaMesLog extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "作业类型")
        @ApiModelProperty(value = "作业类型")
        private String type;
        @Excel(name = "条码")
        @ApiModelProperty(value = "条码")
        private String barcode;
        @Excel(name = "新条码")
        @ApiModelProperty(value = "新条码")
        private String newBarcode;
        @Excel(name = "料号")
        @ApiModelProperty(value = "料号")
        private String itemCode;
        @Excel(name = "数量")
        @ApiModelProperty(value = "数量")
        private Long num;
        @Excel(name = "制造商批号 LOT")
        @ApiModelProperty(value = "制造商批号 LOT")
        private String tblManufacturerBat;
        @Excel(name = "制造商生产日期",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "制造商生产日期")
        private Date tblManufacturerDate;
        @Excel(name = "单据编号")
        @ApiModelProperty(value = "单据编号")
        private String docNo;
        @Excel(name = "作业人")
        @ApiModelProperty(value = "作业人")
        private String createUser;
}
