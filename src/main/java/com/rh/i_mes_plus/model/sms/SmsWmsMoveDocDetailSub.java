package com.rh.i_mes_plus.model.sms;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 调拨单日志操作明细表
 *
 * @author hbq
 * @date 2020-12-11 13:39:17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_wms_move_doc_detail_sub")
public class SmsWmsMoveDocDetailSub extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "调拨物料明细id")
        @ApiModelProperty(value = "调拨物料明细id")
        private Long moveDid;
        @Excel(name = "调拨单号")
        @ApiModelProperty(value = "调拨单号")
        private String moveNo;
        @Excel(name = "调拨物料品号")
        @ApiModelProperty(value = "调拨物料品号")
        private String itemCode;
        @Excel(name = "调拨物料品号")
        @ApiModelProperty(value = "调拨物料品号")
        private String itemName;
        @Excel(name = "调拨物料sn")
        @ApiModelProperty(value = "调拨物料sn")
        private String barcode;
        @Excel(name = "调拨数量")
        @ApiModelProperty(value = "调拨数量")
        private Long amount;
        @Excel(name = "作业员工")
        @ApiModelProperty(value = "作业员工")
        private String workerName;
        @Excel(name = "作业员工号")
        @ApiModelProperty(value = "作业员工号")
        private String worker;
        @Excel(name = "作业时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "作业时间")
        private Date workTime;
        @Excel(name = "是否回写，y为是；n/null为否")
        @ApiModelProperty(value = "是否回写，y为是；n/null为否")
        private String toErp;
        @Excel(name = "装载容器")
        @ApiModelProperty(value = "装载容器")
        private String osContainer;
        @Excel(name = "栈板sn")
        @ApiModelProperty(value = "栈板sn")
        private String palletSn;
}
