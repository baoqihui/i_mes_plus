package com.rh.i_mes_plus.model.pdt;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 入库单表
 *
 * @author hbq
 * @date 2020-12-29 15:41:49
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pdt_wms_receive_doc")
public class PdtWmsReceiveDoc extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "入库单号")
        @ApiModelProperty(value = "入库单号")
        private String docNo;
        @Excel(name = "指令单号")
        @ApiModelProperty(value = "指令单号")
        private String moNo;
        @Excel(name = "物料代码")
        @ApiModelProperty(value = "物料代码")
        private String modelCode;
        @Excel(name = "物料名称")
        @ApiModelProperty(value = "物料名称")
        private String modelName;
        @Excel(name = "版本")
        @ApiModelProperty(value = "版本")
        private String modelVer;
        @Excel(name = "批次")
        @ApiModelProperty(value = "批次")
        private String batch;
        @Excel(name = "入库日期",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "入库日期")
        private Date inDate;
        @Excel(name = "单据类型代码")
        @ApiModelProperty(value = "单据类型代码")
        private String dtCode;
        @Excel(name = "供应商代码")
        @ApiModelProperty(value = "供应商代码")
        private String supperCode;
        @Excel(name = "客户名称")
        @ApiModelProperty(value = "客户名称")
        private String customer;
        @Excel(name = "仓库代码")
        @ApiModelProperty(value = "仓库代码")
        private String whCode;
        @Excel(name = "检验状态(0未送检1已送检2未接检3检验中4允收5拒收5让步接收)")
        @ApiModelProperty(value = "检验状态(0未送检1已送检2未接检3检验中4允收5拒收5让步接收)")
        private String oqaFlag;
        @Excel(name = "录入人")
        @ApiModelProperty(value = "录入人")
        private String createName;
        @Excel(name = "1=录入、2=入库中、3=已审核、4=关结")
        @ApiModelProperty(value = "1=录入、2=入库中、3=已审核、4=关结")
        private String status;
        @Excel(name = "备注")
        @ApiModelProperty(value = "备注")
        private String remark;
        @Excel(name = "部门代码")
        @ApiModelProperty(value = "部门代码")
        private String depaCode;
        @Excel(name = "部门名称")
        @ApiModelProperty(value = "部门名称")
        private String depaName;
}
