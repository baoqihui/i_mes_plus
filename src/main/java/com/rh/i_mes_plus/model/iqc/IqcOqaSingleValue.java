package com.rh.i_mes_plus.model.iqc;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 样品检测项目值
 *
 * @author hbq
 * @date 2020-10-22 16:28:19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("iqc_oqa_single_value")
public class IqcOqaSingleValue extends SuperEntity {
    private static final long serialVersionUID=1L;

    @Excel(name = "抽检单号")
    @ApiModelProperty(value = "抽检单号")
    private String oqcNo;
    @Excel(name = "抽样条码")
    @ApiModelProperty(value = "抽样条码")
    private String osvSerialNumber;
    @Excel(name = "制令号")
    @ApiModelProperty(value = "制令号")
    private String osvMoNumber;
    @Excel(name = "测试项类别ID")
    @ApiModelProperty(value = "测试项类别ID")
    private Long osvTiyId;
    @Excel(name = "测试项名称")
    @ApiModelProperty(value = "测试项名称")
    private String osvTiName;
    @Excel(name = "单位")
    @ApiModelProperty(value = "单位")
    private String osvTimUnit;
    @Excel(name = "显示顺序")
    @ApiModelProperty(value = "显示顺序")
    private Long osvSort;
    @Excel(name = "测试值")
    @ApiModelProperty(value = "测试值")
    private String osvValue;
    @Excel(name = "不良代码")
    @ApiModelProperty(value = "不良代码")
    private String osvErrorCode;
    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String osvDirections;
    @Excel(name = "抽验个体ID")
    @ApiModelProperty(value = "抽验个体ID")
    private Long osId;
    @Excel(name = "报废数量")
    @ApiModelProperty(value = "报废数量")
    private Long osvScarpNumber;
    @Excel(name = "项目检验结果(ok/ng) ")
    @ApiModelProperty(value = "项目检验结果(ok/ng) ")
    private String osvResult;
    @Excel(name = "不良点位1")
    @ApiModelProperty(value = "不良点位1")
    private String osvPoints;
    @Excel(name = "不良点位2")
    @ApiModelProperty(value = "不良点位2")
    private String osvPoints2;
    @Excel(name = "不良点位3")
    @ApiModelProperty(value = "不良点位3")
    private String osvPoints3;
    @Excel(name = "缺陷等级代码 ")
    @ApiModelProperty(value = "缺陷等级代码 ")
    private String odlCode;
    @Excel(name = "")
    @ApiModelProperty(value = "")
    private Long osvTimRange;
    @Excel(name = "下限")
    @ApiModelProperty(value = "下限")
    private String osvTimLowerLimit;
    @Excel(name = "上限")
    @ApiModelProperty(value = "上限")
    private String osvTimUpperLimit;
    @Excel(name = "机构代码")
    @ApiModelProperty(value = "机构代码")
    private String depaCode;
    @Excel(name = "部门名称")
    @ApiModelProperty(value = "部门名称")
    private String depaName;
}
