package com.rh.i_mes_plus.model.iqc;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 样本代码
 *
 * @author hbq
 * @date 2020-10-23 15:24:30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("iqc_co_qc_samples")
public class IqcCoQcSamples extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "检验水平ID")
        @ApiModelProperty(value = "检验水平ID")
        private Long otgId;
        @Excel(name = "批量启始数")
        @ApiModelProperty(value = "批量启始数")
        private Long frLotcount;
        @Excel(name = "批量结束数")
        @ApiModelProperty(value = "批量结束数")
        private Long toLotcount;
        @Excel(name = "样本大小代码")
        @ApiModelProperty(value = "样本大小代码")
        private String codeValues;
        @Excel(name = "机构代码")
        @ApiModelProperty(value = "机构代码")
        private String depaCode;
        @Excel(name = "部门名称")
        @ApiModelProperty(value = "部门名称")
        private String depaName;
}
