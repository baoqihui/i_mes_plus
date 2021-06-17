package com.rh.i_mes_plus.model.sps;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 工装治具借用记录表
 *
 * @author hbq
 * @date 2021-03-09 10:59:40
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("gzk_fix_req_info")
public class GzkFixReqInfo extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "借用单号")
        @ApiModelProperty(value = "借用单号")
        private String reqNo;
        @Excel(name = "工装治具编号")
        @ApiModelProperty(value = "工装治具编号")
        private String fixNo;
        @Excel(name = "小类类别")
        @ApiModelProperty(value = "小类类别")
        private String itemTypeCode;
        @Excel(name = "借用人")
        @ApiModelProperty(value = "借用人")
        private String usrLend;
        @Excel(name = "借用时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "借用时间")
        private Date timeLend;
        @Excel(name = "借用数量")
        @ApiModelProperty(value = "借用数量")
        private Long qtyLend;
        @Excel(name = "归还人")
        @ApiModelProperty(value = "归还人")
        private String usrReturn;
        @Excel(name = "归还时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "归还时间")
        private Date timeReturn;
        @Excel(name = "归还数量")
        @ApiModelProperty(value = "归还数量")
        private Long qtyReturn;
        @Excel(name = "备注")
        @ApiModelProperty(value = "备注")
        private String remark;
        @Excel(name = "状态(1，借用中 2，已关结)")
        @ApiModelProperty(value = "状态(1，借用中 2，已关结)")
        private String state;
}
