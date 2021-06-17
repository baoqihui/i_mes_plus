package com.rh.i_mes_plus.model.iqc;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 检验单
 *
 * @author hbq
 * @date 2020-10-21 14:29:19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("iqc_oqa")
public class IqcOqa extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "抽检单号")
        @ApiModelProperty(value = "抽检单号")
        private String oqcNo;
        @Excel(name = "收料单号（入库单）")
        @ApiModelProperty(value = "收料单号（入库单）")
        private String wrDocNum;
        @Excel(name = "检验阶代码")
        @ApiModelProperty(value = "检验阶代码")
        private String otsCode;
        @Excel(name = "机种代码")
        @ApiModelProperty(value = "机种代码")
        private String itemCode;
        @Excel(name = "生产批次号")
        @ApiModelProperty(value = "生产批次号")
        private String lotNumber;
        @Excel(name = "区域ID(线别)")
        @ApiModelProperty(value = "区域ID(线别)")
        private String lineCode;
        @Excel(name = "客户代码")
        @ApiModelProperty(value = "客户代码")
        private String custCode;
        @Excel(name = "检验次数")
        @ApiModelProperty(value = "检验次数")
        private String oqcReCheck;
        @Excel(name = "送检数量")
        @ApiModelProperty(value = "送检数量")
        private Long oqcSendAmount;
        @Excel(name = "样本数量")
        @ApiModelProperty(value = "应抽数量")
        private Long oqcSampleAmount;
        @Excel(name = "抽检数量")
        @ApiModelProperty(value = "抽检数量")
        private Long oqcTestAmount;
        @Excel(name = "NG数量(轻缺陷数量)")
        @ApiModelProperty(value = "NG数量(轻缺陷数量)")
        private Long oqcNgPcb;
        @Excel(name = "报废数量")
        @ApiModelProperty(value = "报废数量")
        private Long oqcScrapQty;
        @Excel(name = "拒收数 ")
        @ApiModelProperty(value = "拒收数 ")
        private Long ngNum;
        @Excel(name = "允收数")
        @ApiModelProperty(value = "允收数")
        private Long okNum;
        @Excel(name = "回流工序ID")
        @ApiModelProperty(value = "回流工序ID")
        private Long oqcRouteGroupid;
        @Excel(name = "回流工序名称")
        @ApiModelProperty(value = "回流工序名称")
        private String oqcGroupName;
        @Excel(name = "制令号")
        @ApiModelProperty(value = "制令号")
        private String moNumber;
        @Excel(name = "检验员")
        @ApiModelProperty(value = "检验员")
        private String oqcExaminer;
        @Excel(name = "开单人 ")
        @ApiModelProperty(value = "开单人 ")
        private String oqcCreator;
        @Excel(name = "送检时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "送检时间")
        private Date oqcSendDate;
        @Excel(name = "判定时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "判定时间")
        private Date oqcResultDate;
        @Excel(name = "状态(0-待检 1-检验中 2-检验完成 3-已审核 4-已撤销)")
        @ApiModelProperty(value = "状态(0-待检 1-检验中 2-检验完成 3-已审核 4-已撤销)")
        private Integer oqcStatus;
        @Excel(name = "审核人")
        @ApiModelProperty(value = "审核人")
        private String oqcAudit;
        @Excel(name = "抽验结果 1-允收 2-拒收 3-让步接收4-批退")
        @ApiModelProperty(value = "抽验结果 1-允收 2-拒收 3-让步接收4-批退")
        private Integer oqcResult;
        @Excel(name = "审核时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "审核时间")
        private Date oqcAuditTime;
        @Excel(name = "备注")
        @ApiModelProperty(value = "备注")
        private String oqcAuditRemark;
        @Excel(name = "部门代码")
        @ApiModelProperty(value = "部门代码")
        private String depaCode;
        @Excel(name = "部门名称")
        @ApiModelProperty(value = "部门名称")
        private String depaName;
}
