package com.rh.i_mes_plus.model.iqc;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * OQA抽检历史记录
 *
 * @author hbq
 * @date 2020-10-22 16:28:19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("iqc_oqa_history")
public class IqcOqaHistory extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "抽检单号")
        @ApiModelProperty(value = "抽检单号")
        private String oqcNo;
        @Excel(name = "检验阶代码")
        @ApiModelProperty(value = "检验阶代码")
        private String otsCode;
        @Excel(name = "制令号")
        @ApiModelProperty(value = "制令号")
        private String moNumber;
        @Excel(name = "机种代码")
        @ApiModelProperty(value = "机种代码")
        private String itemCode;
        @Excel(name = "生产批次号")
        @ApiModelProperty(value = "生产批次号")
        private String lotNumber;
        @Excel(name = "区域ID(线别)")
        @ApiModelProperty(value = "区域ID(线别)")
        private Long areaid;
        @Excel(name = "客户代码")
        @ApiModelProperty(value = "客户代码")
        private String custCode;
        @Excel(name = "状态(0-待检 1-检验中 2-检验完成 3-已审核 4-已撤销)")
        @ApiModelProperty(value = "状态(0-待检 1-检验中 2-检验完成 3-已审核 4-已撤销)")
        private String oqcStatus;
        @Excel(name = "送检时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "送检时间")
        private Date oqcSendDate;
        @Excel(name = "判定时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "判定时间")
        private Date oqcResultDate;
        @Excel(name = "检验次数")
        @ApiModelProperty(value = "检验次数")
        private Long oqcReCheck;
        @Excel(name = "送检数量")
        @ApiModelProperty(value = "送检数量")
        private Long oqcSendAmount;
        @Excel(name = "样本数量")
        @ApiModelProperty(value = "样本数量")
        private Long oqcSampleAmount;
        @Excel(name = "抽检数量")
        @ApiModelProperty(value = "抽检数量")
        private Long oqcTestAmount;
        @Excel(name = "NG数量")
        @ApiModelProperty(value = "NG数量")
        private Long oqcNgPcb;
        @Excel(name = "抽验结果 1-允收 2-拒收 3-让步接收4-批退")
        @ApiModelProperty(value = "抽验结果 1-允收 2-拒收 3-让步接收4-批退")
        private String oqcResult;
        @Excel(name = "检验员")
        @ApiModelProperty(value = "检验员")
        private String oqcExaminer;
        @Excel(name = "审核人")
        @ApiModelProperty(value = "审核人")
        private String oqcAudit;
        @Excel(name = "备注")
        @ApiModelProperty(value = "备注")
        private String oqcRemark;
        @Excel(name = "审核时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "审核时间")
        private Date oqcAuditTime;
        @Excel(name = "部门代码")
        @ApiModelProperty(value = "部门代码")
        private String depaCode;
        @Excel(name = "部门名称")
        @ApiModelProperty(value = "部门名称")
        private String depaName;
}
