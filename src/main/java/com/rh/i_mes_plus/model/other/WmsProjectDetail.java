package com.rh.i_mes_plus.model.other;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工单明细表
 *
 * @author hbq
 * @date 2020-10-31 13:40:47
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wms_project_detail")
public class WmsProjectDetail extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "工单ID")
        @ApiModelProperty(value = "工单ID")
        private String projectId;
        @Excel(name = "工单BOM明细料号")
        @ApiModelProperty(value = "工单BOM明细料号")
        private String itemCode;
        @Excel(name = "应发量")
        @ApiModelProperty(value = "应发量")
        private Long mustProvideQty;
        @Excel(name = "已发量")
        @ApiModelProperty(value = "已发量")
        private Long hadProvideQty;
        @Excel(name = "品名")
        @ApiModelProperty(value = "品名")
        private String itemName;
        @Excel(name = "单位")
        @ApiModelProperty(value = "单位")
        private String units;
        @Excel(name = "超领量")
        @ApiModelProperty(value = "超领量")
        private Long exceedQty;
        @Excel(name = "报废量")
        @ApiModelProperty(value = "报废量")
        private Long scrapQty;
        @Excel(name = "BOM数量")
        @ApiModelProperty(value = "BOM数量")
        private Long bomQty;
        @Excel(name = "替代码")
        @ApiModelProperty(value = "替代码")
        private String subItemCode;
        @Excel(name = "备料标识")
        @ApiModelProperty(value = "备料标识")
        private String stockFlag;
        @Excel(name = "机构代码")
        @ApiModelProperty(value = "机构代码")
        private String depaCode;
        @Excel(name = "s 正面 c 反面（跟制令单定义一致）)")
        @ApiModelProperty(value = "s 正面 c 反面（跟制令单定义一致）)")
        private String scFlag;
        @Excel(name = "BOM行号")
        @ApiModelProperty(value = "BOM行号")
        private String erpItem;
        @Excel(name = "实际使用量")
        @ApiModelProperty(value = "实际使用量")
        private Long realityQty;
}
