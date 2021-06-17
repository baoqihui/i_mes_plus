package com.rh.i_mes_plus.model.iqc;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 抽验样本信息
 *
 * @author hbq
 * @date 2020-10-23 11:39:35
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("iqc_oqa_single")
public class IqcOqaSingle extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "抽检单号")
        @ApiModelProperty(value = "抽检单号")
        private String oqcNo;
        @Excel(name = "抽验ID")
        @ApiModelProperty(value = "抽验ID")
        private Long otId;
        @Excel(name = "抽样条码")
        @ApiModelProperty(value = "抽样条码")
        private String serialNumber;
        @Excel(name = "样本号")
        @ApiModelProperty(value = "样本号")
        private Long osNo;
        @Excel(name = "抽验结果 OK NG ")
        @ApiModelProperty(value = "抽验结果 OK NG ")
        private String osResult;
        @Excel(name = "抽检时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "抽检时间")
        private Date osTestDate;
        @Excel(name = "检验员")
        @ApiModelProperty(value = "检验员")
        private String osExaminer;
        @Excel(name = "数量")
        @ApiModelProperty(value = "数量")
        private Long osAmount;
        @Excel(name = "备注")
        @ApiModelProperty(value = "备注")
        private String osRemark;
        @Excel(name = "回流工序")
        @ApiModelProperty(value = "回流工序")
        private String osGroupName;
        @Excel(name = "回流工序ID")
        @ApiModelProperty(value = "回流工序ID")
        private Long osGroupId;
        @Excel(name = "包装SN")
        @ApiModelProperty(value = "包装SN")
        private String containerSn;
        @Excel(name = "报废数")
        @ApiModelProperty(value = "报废数")
        private Long scrapQty;
        @Excel(name = "机构代码")
        @ApiModelProperty(value = "机构代码")
        private String depaCode;
        @Excel(name = "部门名称")
        @ApiModelProperty(value = "部门名称")
        private String depaName;
}
