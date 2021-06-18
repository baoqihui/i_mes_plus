package com.rh.i_mes_plus.model.other;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ECC生产BOM
 *
 * @author hbq
 * @date 2021-01-09 15:34:09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ecc_material_detail")
public class EccMaterialDetail extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "工单号")
        @ApiModelProperty(value = "工单号")
        private String woNo;
        @Excel(name = "行号")
        @ApiModelProperty(value = "行号")
        private Integer lineNo;
        @Excel(name = "物料编号")
        @ApiModelProperty(value = "物料编号")
        private String materialPn;
        @Excel(name = "物料名称")
        @ApiModelProperty(value = "物料名称")
        private String materialName;
        @Excel(name = "单板用量")
        @ApiModelProperty(value = "单板用量")
        private Long panelQty;
        @Excel(name = "总数")
        @ApiModelProperty(value = "总数")
        private Long totalQty;
        @Excel(name = "已扣数量")
        @ApiModelProperty(value = "已扣数量")
        private Long hasSendQty;
        @Excel(name = "替代组")
        @ApiModelProperty(value = "替代组")
        private String subGroup;
        @Excel(name = "正反面标识")
        @ApiModelProperty(value = "正反面标识")
        private String scFlag;
}
