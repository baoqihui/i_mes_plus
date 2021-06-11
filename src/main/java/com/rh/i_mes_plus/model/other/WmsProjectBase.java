package com.rh.i_mes_plus.model.other;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 工单信息主表
 *
 * @author hbq
 * @date 2021-05-27 08:41:55
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wms_project_base")
public class WmsProjectBase extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "工单号")
        @ApiModelProperty(value = "工单号")
        private String projectId;
        @Excel(name = "工单状态1、开立  5、上线  8、关结")
        @ApiModelProperty(value = "工单状态1、开立  5、上线  8、关结")
        private String projectStatus;
        @Excel(name = "生产数量")
        @ApiModelProperty(value = "生产数量")
        private Long productQty;
        @Excel(name = "机种(成品或半成品或烧录后)")
        @ApiModelProperty(value = "机种(成品或半成品或烧录后)")
        private String itemCode;
        @Excel(name = "品名")
        @ApiModelProperty(value = "品名")
        private String itemName;
        @Excel(name = "规格")
        @ApiModelProperty(value = "规格")
        private String itemDesc;
        @Excel(name = "工单类型（1，正常 2，试产）")
        @ApiModelProperty(value = "工单类型（1，正常 2，试产）")
        private String projectType;
        @Excel(name = "工单阶别")
        @ApiModelProperty(value = "工单阶别")
        private String msCode;
        @Excel(name = "批次号")
        @ApiModelProperty(value = "批次号")
        private String dataCode;
        @Excel(name = "投入量")
        @ApiModelProperty(value = "投入量")
        private Long fqcQty;
        @Excel(name = "产出数量")
        @ApiModelProperty(value = "产出数量")
        private Long finishQty;
        @Excel(name = "报废数量")
        @ApiModelProperty(value = "报废数量")
        private Long scrappedQty;
        @Excel(name = "预计开工日期",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "预计开工日期")
        private Date prolepsisStartDate;
        @Excel(name = "预计完成时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "预计完成时间")
        private Date prolepsisEndDate;
        @Excel(name = "实际开始时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "实际开始时间")
        private Date projectStartDate;
        @Excel(name = "工关结时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "工关结时间")
        private Date projectEndDate;
        @Excel(name = "版本:")
        @ApiModelProperty(value = "版本:")
        private String ver;
        @Excel(name = "销单单号: ")
        @ApiModelProperty(value = "销单单号: ")
        private String destroyNo;
        @Excel(name = "工单展开阶数:0")
        @ApiModelProperty(value = "工单展开阶数:0")
        private String openLadder;
        @Excel(name = "冻结与否")
        @ApiModelProperty(value = "冻结与否")
        private String freezeFlag;
        @Excel(name = "优先顺序")
        @ApiModelProperty(value = "优先顺序")
        private String priority;
        @Excel(name = "紧急比率")
        @ApiModelProperty(value = "紧急比率")
        private Long instancyRate;
        @Excel(name = "")
        @ApiModelProperty(value = "")
        private String remarkDept;
        @Excel(name = "项目号码(无效)")
        @ApiModelProperty(value = "项目号码(无效)")
        private String projectNo;
        @Excel(name = "发放日期",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "发放日期")
        private Date provideDate;
        @Excel(name = "工单开立时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "工单开立时间")
        private Date projectOpenTime;
        @Excel(name = "入库数量")
        @ApiModelProperty(value = "入库数量")
        private Long instorageQty;
        @Excel(name = "已排程标识")
        @ApiModelProperty(value = "已排程标识")
        private String planFlag;
        @Excel(name = "加工面数(1—单面 2—双面)")
        @ApiModelProperty(value = "加工面数(1—单面 2—双面)")
        private Long workingSc;
        @Excel(name = "订单号")
        @ApiModelProperty(value = "订单号")
        private String orderNumber;
        @Excel(name = "客户代码")
        @ApiModelProperty(value = "客户代码")
        private String custCode;
        @Excel(name = "计划交货日期",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "计划交货日期")
        private Date tppbPlanDeliveryDate;
        @Excel(name = "修改人")
        @ApiModelProperty(value = "修改人")
        private String editor;
        @Excel(name = "工单开单人")
        @ApiModelProperty(value = "工单开单人")
        private String projectCreater;
        @Excel(name = "线别代码")
        @ApiModelProperty(value = "线别代码")
        private String lineCode;
        @Excel(name = "抽样批次大小")
        @ApiModelProperty(value = "抽样批次大小")
        private Long oqcSendAmount;
        @Excel(name = "机构代码")
        @ApiModelProperty(value = "机构代码")
        private String depaCode;
        @Excel(name = "QN开始号段")
        @ApiModelProperty(value = "QN开始号段")
        private String qnBegin;
        @Excel(name = "QN结束号段")
        @ApiModelProperty(value = "QN结束号段")
        private String qnEnd;
        @Excel(name = "MAC开始号段")
        @ApiModelProperty(value = "MAC开始号段")
        private String macBegin;
        @Excel(name = "MAC结束号段")
        @ApiModelProperty(value = "MAC结束号段")
        private String macEnd;
        @Excel(name = "SAS开始地址")
        @ApiModelProperty(value = "SAS开始地址")
        private String sasBegin;
        @Excel(name = "SAS结束地址")
        @ApiModelProperty(value = "SAS结束地址")
        private String sasEnd;
        @Excel(name = "ERP回传状态")
        @ApiModelProperty(value = "ERP回传状态")
        private String erpSendStatus;
        @Excel(name = "成本中心")
        @ApiModelProperty(value = "成本中心")
        private String costStation;
        @Excel(name = "父级工单")
        @ApiModelProperty(value = "父级工单")
        private String fatherProject;
        @Excel(name = "产品生产阶段（量产，试产）")
        @ApiModelProperty(value = "产品生产阶段（量产，试产）")
        private String productState;
        @Excel(name = "备注")
        @ApiModelProperty(value = "备注")
        private String pmMemo;
        @Excel(name = "关联ecn")
        @ApiModelProperty(value = "关联ecn")
        private String ecns;

}
