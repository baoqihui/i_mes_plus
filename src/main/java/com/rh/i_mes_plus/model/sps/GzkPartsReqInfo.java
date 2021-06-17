package com.rh.i_mes_plus.model.sps;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 工装备品借用记录表
 *
 * @author hbq
 * @date 2021-02-21 10:39:50
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("gzk_parts_req_info")
public class GzkPartsReqInfo extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "借用单号")
        @ApiModelProperty(value = "借用单号")
        private String reqNo;
        @Excel(name = "KGT编号")
        @ApiModelProperty(value = "KGT编号")
        private String kgtNo;
        @Excel(name = "备品类别")
        @ApiModelProperty(value = "备品类别")
        private String itemTypeCode;
        @Excel(name = "借用人")
        @ApiModelProperty(value = "借用人")
        private String usrLend;
        @Excel(name = "借用时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "借用时间")
        private Date timeLend;
        @Excel(name = "借用天数")
        @ApiModelProperty(value = "借用天数")
        private String dateLend;
        @Excel(name = "借用位置（生产/测试）")
        @ApiModelProperty(value = "借用位置（生产/测试）")
        private String loc;
        @Excel(name = "工装治具编号")
        @ApiModelProperty(value = "工装治具编号")
        private String gzFixNo;
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
        @Excel(name = "状态")
        @ApiModelProperty(value = "状态")
        private String state;
}
