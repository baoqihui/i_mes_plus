package com.rh.i_mes_plus.model.sps;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 工程治具借用记录表
 *
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sps_eng_fix_req_info")
public class SpsEngFixReqInfo extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "申请单号")
        @ApiModelProperty(value = "申请单号")
        private String reqNo;
        @Excel(name = "制令单号")
        @ApiModelProperty(value = "制令单号")
        private String moNo;
        @Excel(name = "借用人")
        @ApiModelProperty(value = "借用人")
        private String usrLend;
        @Excel(name = "小类类别")
        @ApiModelProperty(value = "小类类别")
        private String itemTypeCode;
        @Excel(name = "机型")
        @ApiModelProperty(value = "机型")
        private String modelCode;
        @Excel(name = "借用时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "借用时间")
        private Date timeLend;
        @Excel(name = "归还人")
        @ApiModelProperty(value = "归还人")
        private String usrReturn;
        @Excel(name = "归还时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "归还时间")
        private Date timeReturn;
        @Excel(name = "存储位置")
        @ApiModelProperty(value = "存储位置")
        private String poses;
        @Excel(name = "工单数量")
        @ApiModelProperty(value = "工单数量")
        private Long targetQty;
        @Excel(name = "状态")
        @ApiModelProperty(value = "状态")
        private String state;
        @Excel(name = "备注")
        @ApiModelProperty(value = "备注")
        private String remark;
}
