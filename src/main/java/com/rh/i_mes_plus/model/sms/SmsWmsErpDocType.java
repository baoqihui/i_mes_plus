package com.rh.i_mes_plus.model.sms;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * ERP单据性质档
 *
 * @author hbq
 * @date 2021-01-19 10:01:03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_wms_erp_doc_type")
public class SmsWmsErpDocType extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "单据类别")
        @ApiModelProperty(value = "单据类别")
        private String dtCode;
        @Excel(name = "类型id")
        @ApiModelProperty(value = "类型id")
        private Long dId;
        @Excel(name = "单别名称")
        @ApiModelProperty(value = "单别名称")
        private String typeName;
        @Excel(name = "创建人工号")
        @ApiModelProperty(value = "创建人工号")
        private String createErpNo;
        @Excel(name = "单别编码")
        @ApiModelProperty(value = "单别编码")
        private String typeNo;
        @Excel(name = "创建人姓名",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "创建人姓名")
        private Date createRepName;
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
