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
 * @date 2020-12-16 14:55:14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_wms_reload_doc_detail_sub")
public class SmsWmsReloadDocDetailSub extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "换料物料明细id")
        @ApiModelProperty(value = "换料物料明细id")
        private Long reloadDid;
        @Excel(name = "换料单号")
        @ApiModelProperty(value = "换料单号")
        private String reloadNo;
        @Excel(name = "换料物料品号")
        @ApiModelProperty(value = "换料物料品号")
        private String itemCode;
        @Excel(name = "换料物料sn")
        @ApiModelProperty(value = "换料物料sn")
        private String barcode;
        @Excel(name = "作业员工号")
        @ApiModelProperty(value = "作业员工号")
        private String worker;
        @Excel(name = "作业时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "作业时间")
        private Date workTime;
        @Excel(name = "是否回写，y为是；n/null为否")
        @ApiModelProperty(value = "是否回写，y为是；n/null为否")
        private String toErp;
        @Excel(name = "数量")
        @ApiModelProperty(value = "数量")
        private Long amount;
}
