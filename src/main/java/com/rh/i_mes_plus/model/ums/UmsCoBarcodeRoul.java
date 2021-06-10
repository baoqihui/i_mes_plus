package com.rh.i_mes_plus.model.ums;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 物料条码规则
 *
 * @author hqb
 * @date 2020-09-23 17:16:07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_co_barcode_roul")
public class UmsCoBarcodeRoul extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "规则名称")
        @ApiModelProperty(value = "规则名称")
        private String tbrName;
        @Excel(name = "匹配字符 -:物料单一字符 *:物料任意字符 ?:非物料任意字符")
        @ApiModelProperty(value = "匹配字符 -:物料单一字符 *:物料任意字符 ?:非物料任意字符")
        private String tbrMatchChar;
        @Excel(name = "备注")
        @ApiModelProperty(value = "备注")
        private String tbrMemo;
        @Excel(name = "转换标识")
        @ApiModelProperty(value = "转换标识")
        private Long tbrYn;
        @Excel(name = "有效标识")
        @ApiModelProperty(value = "有效标识")
        private String tbrStatus;
        @Excel(name = "0 物料 1 包装箱")
        @ApiModelProperty(value = "0 物料 1 包装箱")
        private String tbrType;
        @Excel(name = "流水码位置（0-无1-左边2-右边）")
        @ApiModelProperty(value = "流水码位置（0-无1-左边2-右边）")
        private Long tbrSnLoc;
        @Excel(name = "流水码起始位，从1计数")
        @ApiModelProperty(value = "流水码起始位，从1计数")
        private Long tbrSnInd;
        @Excel(name = "流水码长度")
        @ApiModelProperty(value = "流水码长度")
        private Long tbrSnLen;
        @TableLogic
        @ApiModelProperty(value = "",hidden = true)
        private Boolean isDel;
        @Excel(name = "部门代码")
        @ApiModelProperty(value = "部门代码")
        private String depaCode;
        @Excel(name = "部门名称")
        @ApiModelProperty(value = "部门名称")
        private String depaName;
}
