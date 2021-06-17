package com.rh.i_mes_plus.model.sps;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 工装治具
 *
 * @author hbq
 * @date 2021-02-23 10:06:16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("gzk_fix_detail_info")
public class GzkFixDetailInfo extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "治具编号")
        @ApiModelProperty(value = "治具编号")
        private String fixNo;
        @Excel(name = "治具大类代码")
        @ApiModelProperty(value = "治具大类代码")
        private String typeCode;
        @Excel(name = "小类别代码")
        @ApiModelProperty(value = "小类别代码")
        private String itemTypeCode;
        @Excel(name = "适用机型")
        @ApiModelProperty(value = "适用机型")
        private String modelCode;
        @Excel(name = "供应商代码")
        @ApiModelProperty(value = "供应商代码")
        private String supplierCode;
        @Excel(name = "储存位置")
        @ApiModelProperty(value = "储存位置")
        private String pos;
        @Excel(name = "进场日期",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "进场日期")
        private Date inDate;
        @Excel(name = "创建人")
        @ApiModelProperty(value = "创建人")
        private String createUser;
        @Excel(name = "状态（在库/已借出/报废）",replace = {"在库_1", "借出_2","报废_3"})
        @ApiModelProperty(value = "状态（在库/已借出/报废）")
        private String state;
}
