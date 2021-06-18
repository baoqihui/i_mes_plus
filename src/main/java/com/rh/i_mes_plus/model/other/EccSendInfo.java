package com.rh.i_mes_plus.model.other;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * ECC扣料数据
 *
 * @author hbq
 * @date 2021-01-09 15:34:09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ecc_send_info")
public class EccSendInfo extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "请求号")
        @ApiModelProperty(value = "请求号")
        private String requestNo;
        @Excel(name = "批次数量")
        @ApiModelProperty(value = "批次数量")
        private Long batchCount;
        @Excel(name = "批次号")
        @ApiModelProperty(value = "批次号")
        private String batchcode;
        @Excel(name = "板卡物料")
        @ApiModelProperty(value = "板卡物料")
        private String materialcode;
        @Excel(name = "")
        @ApiModelProperty(value = "")
        private Long shipCount;
        @Excel(name = "交货时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "交货时间")
        private Date shipDate;
        @Excel(name = "采购数量")
        @ApiModelProperty(value = "采购数量")
        private Long count;
        @Excel(name = "组件物料号")
        @ApiModelProperty(value = "组件物料号")
        private String componentcode;
        @Excel(name = "组件用料数量")
        @ApiModelProperty(value = "组件用料数量")
        private Long componentcount;
        @Excel(name = "交货行号")
        @ApiModelProperty(value = "交货行号")
        private Integer linecode;
        @Excel(name = "单位")
        @ApiModelProperty(value = "单位")
        private String unit;
        @Excel(name = "采购订单")
        @ApiModelProperty(value = "采购订单")
        private String ordercode;
        @Excel(name = "供应商")
        @ApiModelProperty(value = "供应商")
        private String suppliercode;
        @Excel(name = "工厂")
        @ApiModelProperty(value = "工厂")
        private String factory;
        @Excel(name = "工单号")
        @ApiModelProperty(value = "工单号")
        private String woNo;
        @Excel(name = "工单数")
        @ApiModelProperty(value = "工单数")
        private Long woNoCount;
        @Excel(name = "销售发货单号")
        @ApiModelProperty(value = "销售发货单号")
        private String docId;
}
