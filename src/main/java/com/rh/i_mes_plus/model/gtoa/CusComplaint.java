package com.rh.i_mes_plus.model.gtoa;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 客诉表
 *
 * @author hbq
 * @date 2020-12-23 19:01:39
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("cus_complaint")
public class CusComplaint extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "客户名称")
        @ApiModelProperty(value = "客户名称")
        private String customer;
        @Excel(name = "客诉编号")
        @ApiModelProperty(value = "客诉编号")
        private String cusNo;
        @Excel(name = "是否维修")
        @ApiModelProperty(value = "是否维修")
        private Boolean isMaintain;
        @Excel(name = "反馈日期",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "反馈日期")
        private Date dateAsk;
        @Excel(name = "8D编号")
        @ApiModelProperty(value = "8D编号")
        private String noD8;
        @Excel(name = "产品型号")
        @ApiModelProperty(value = "产品型号")
        private String modelName;
        @Excel(name = "发生地")
        @ApiModelProperty(value = "发生地")
        private String occure;
        @Excel(name = "产品阶段")
        @ApiModelProperty(value = "产品阶段")
        private String phase;
        @Excel(name = "不良现象")
        @ApiModelProperty(value = "不良现象")
        private String expAppearance;
        @Excel(name = "不良位置")
        @ApiModelProperty(value = "不良位置")
        private String expLoc;
        @Excel(name = "批次/SN/QN信息")
        @ApiModelProperty(value = "批次/SN/QN信息")
        private String sn;
        @Excel(name = "不良图片")
        @ApiModelProperty(value = "不良图片")
        private String pic;
        @Excel(name = "是否重复发生（0/1）")
        @ApiModelProperty(value = "是否重复发生（0/1）")
        private Boolean repeatFlag;
        @Excel(name = "投入数")
        @ApiModelProperty(value = "投入数")
        private Long inputQty;
        @Excel(name = "不良数量")
        @ApiModelProperty(value = "不良数量")
        private Long errQty;
        @Excel(name = "临时对策（库存产品的挑选及其不良率）")
        @ApiModelProperty(value = "临时对策（库存产品的挑选及其不良率）")
        private String shortPeriod;
        @Excel(name = "原因分析")
        @ApiModelProperty(value = "原因分析")
        private String expReason;
        @Excel(name = "原因分析图片")
        @ApiModelProperty(value = "原因分析图片")
        private String expReasonImgs;
        @Excel(name = "改善对策")
        @ApiModelProperty(value = "改善对策")
        private String longPeriod;
        @Excel(name = "改善对策图片")
        @ApiModelProperty(value = "改善对策图片")
        private String longPeriodImg;
        @Excel(name = "对策落实时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "对策落实时间")
        private Date datePeriod;
        @Excel(name = "发生原因归属")
        @ApiModelProperty(value = "发生原因归属")
        private String rsp;
        @Excel(name = "责任部门")
        @ApiModelProperty(value = "责任部门")
        private String rspDept;
        @Excel(name = "责任主管")
        @ApiModelProperty(value = "责任主管")
        private String rspManage;
        @Excel(name = "责任人")
        @ApiModelProperty(value = "责任人")
        private String rspUsr;
        @Excel(name = "漏检人")
        @ApiModelProperty(value = "漏检人")
        private String missPeople;
        @Excel(name = "状态")
        @ApiModelProperty(value = "状态")
        private String status;
        @Excel(name = "客户是否同意关闭(0/1)")
        @ApiModelProperty(value = "客户是否同意关闭(0/1)")
        private Boolean closeFlag;
        @Excel(name = "备注")
        @ApiModelProperty(value = "备注")
        private String remark;
}
