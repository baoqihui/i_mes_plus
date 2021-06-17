package com.rh.i_mes_plus.model.iqc;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 抽样项目
 *
 * @author hbq
 * @date 2020-10-22 16:28:19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("iqc_oqa_item")
public class IqcOqaItem extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "抽检单号")
        @ApiModelProperty(value = "抽检单号")
        private String oqcNo;
        @Excel(name = "检验项目ID")
        @ApiModelProperty(value = "检验项目ID")
        private Long tiyId;
        @Excel(name = "抽样方案类型名")
        @ApiModelProperty(value = "抽样方案类型名")
        private String ottName;
        @Excel(name = "检验水平ID")
        @ApiModelProperty(value = "检验水平ID")
        private Long otgId;
        @Excel(name = "AQL值")
        @ApiModelProperty(value = "AQL值")
        private Double oiAql;
        @Excel(name = "AC")
        @ApiModelProperty(value = "AC")
        private String oiAc;
        @Excel(name = "RE")
        @ApiModelProperty(value = "RE")
        private String oiRe;
        @Excel(name = "NG板数")
        @ApiModelProperty(value = "NG板数")
        private Long oiNgCount;
        @Excel(name = "应抽检数")
        @ApiModelProperty(value = "应抽检数")
        private Long oiSampleQyt;
        @Excel(name = "已抽检数")
        @ApiModelProperty(value = "已抽检数")
        private Long oiCheckQyt;
        @Excel(name = "判定结果(NG/OK)")
        @ApiModelProperty(value = "判定结果(NG/OK)")
        private String oiResult;
        @Excel(name = "是否做为检验单判定标准(0/1)")
        @ApiModelProperty(value = "是否做为检验单判定标准(0/1)")
        private Boolean isCheck;
        @Excel(name = "缺陷等级代码 ")
        @ApiModelProperty(value = "缺陷等级代码 ")
        private String odlCode;
        @Excel(name = "机构代码")
        @ApiModelProperty(value = "机构代码")
        private String depaCode;
        @Excel(name = "部门名称")
        @ApiModelProperty(value = "部门名称")
        private String depaName;
}
