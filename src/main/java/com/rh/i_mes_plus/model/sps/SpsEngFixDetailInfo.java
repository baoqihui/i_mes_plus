package com.rh.i_mes_plus.model.sps;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工程治具详情表
 *
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sps_eng_fix_detail_info")
public class SpsEngFixDetailInfo extends SuperEntity {
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
        @Excel(name = "正反面标识")
        @ApiModelProperty(value = "正反面标识")
        private String scFlag;
        @Excel(name = "供应商代码")
        @ApiModelProperty(value = "供应商代码")
        private String supplierCode;
        @Excel(name = "客户代码")
        @ApiModelProperty(value = "客户代码")
        private String custCode;
        @Excel(name = "Pin数")
        @ApiModelProperty(value = "Pin数")
        private Long pinCount;
        @Excel(name = "最大使用次数")
        @ApiModelProperty(value = "最大使用次数")
        private Long maxTimes;
        @Excel(name = "使用次数")
        @ApiModelProperty(value = "使用次数")
        private Long usedTimes;
        @Excel(name = "治具产权",replace = { "自费_1", "非自费_2"})
        @ApiModelProperty(value = "治具产权")
        private String ownership;
        @Excel(name = "储位")
        @ApiModelProperty(value = "储位")
        private String pos;
        @Excel(name = "描述")
        @ApiModelProperty(value = "描述")
        private String fixDesc;
        @Excel(name = "状态",replace = { "在库_1", "借出_2","报废_3","保养_4" })
        @ApiModelProperty(value = "状态")
        private String state;
}
