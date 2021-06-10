package com.rh.i_mes_plus.model.gtoa;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * ECN详情表
 *
 * @author hbq
 * @date 2020-10-22 19:36:56
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ecn_detail_info")
public class EcnDetailInfo extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "ECN")
        @ApiModelProperty(value = "ECN")
        private String ecnNo;
        @Excel(name = "外部部ECN编号")
        @ApiModelProperty(value = "外部ECN编号")
        private String ecnNoCust;
        @Excel(name = "变更原因")
        @ApiModelProperty(value = "变更原因")
        private String reasonOfChange;
        @Excel(name = "变更依据")
        @ApiModelProperty(value = "变更依据")
        private String according;
        @Excel(name = "变更可行性评审类型（1，正式评审（见《ECN可行性评审表》Gt-4-QC-120)  2，一般评审（沟通后可执行））")
        @ApiModelProperty(value = "变更可行性评审类型（1，正式评审（见《ECN可行性评审表》Gt-4-QC-120)  2，一般评审（沟通后可执行））")
        private Integer feasibilityType;
        @Excel(name = "是否通知相关人员")
        @ApiModelProperty(value = "是否通知相关人员")
        private Boolean noticeFlag;
        @Excel(name = "更新纸质BOM")
        @ApiModelProperty(value = "更新纸质BOM")
        private Boolean updateBomFlag;
        @Excel(name = "是否通知客户")
        @ApiModelProperty(value = "是否通知客户")
        private Boolean noticeCusFlag;
        @Excel(name = "成本增加标识")
        @ApiModelProperty(value = "成本增加标识")
        private Boolean costIncreaseFlag;
        @Excel(name = "变更类型(tciIds 使用,分割)")
        @ApiModelProperty(value = "变更类型(tciIds 使用,分割)")
        private String typeOfChange;
        @Excel(name = "变更项目(iciIds 使用,分割)")
        @ApiModelProperty(value = "变更项目(iciIds 使用,分割)")
        private String itemOfChange;
        @Excel(name = "执行方式(emiIds 使用,分割)")
        @ApiModelProperty(value = "执行方式(emiIds 使用,分割)")
        private String executedMode;
        @Excel(name = "分发部门（depaCodes 使用,分割）")
        @ApiModelProperty(value = "分发部门（depaCodes 使用,分割）")
        private String distributedDept;
        @Excel(name = "附件")
        @ApiModelProperty(value = "附件")
        private String accessory;
        @Excel(name = "创建人")
        @ApiModelProperty(value = "创建人")
        private String createName;
        @Excel(name = "编辑人",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "编辑人")
        private String updateName;
        @Excel(name = "审核人")
        @ApiModelProperty(value = "审核人")
        private String auditName;
        @Excel(name = "审核时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "审核时间")
        private Date auditTime;
        @Excel(name = "审核状态(0,草稿 1，未审核 2，审核通过 3，审核不通过 4，信息完善)")
        @ApiModelProperty(value = "审核状态(0,草稿 1，未审核 2，审核通过 3，审核不通过 4，信息完善)")
        private Integer auditStatus;
        @Excel(name = "审核原因")
        @ApiModelProperty(value = "审核原因")
        private String auditRemark;
        @TableField(strategy = FieldStrategy.IGNORED)
        @Excel(name = "生效时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "生效时间")
        private Date validBeginDate;
        @TableField(strategy = FieldStrategy.IGNORED)
        @Excel(name = "截至时间(2099-12-31 23:59:59可定义日期或永久有效)")
        @ApiModelProperty(value = "截至时间(2099-12-31 23:59:59可定义日期或永久有效)")
        private String validEndDate;
        @Excel(name = "时间完善时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "时间完善时间")
        private Date validTime;
        @Excel(name = "可发货版本")
        @ApiModelProperty(value = "可发货版本")
        private String validDeliveryVer;
        @Excel(name = "是否需要fai")
        @ApiModelProperty(value = "是否需要fai")
        private Boolean hasFai;
        @Excel(name = "是否挂起")
        @ApiModelProperty(value = "是否挂起")
        private Boolean isHangUp;
        @Excel(name = "批次")
        @ApiModelProperty(value = "批次")
        private String batch;
}
