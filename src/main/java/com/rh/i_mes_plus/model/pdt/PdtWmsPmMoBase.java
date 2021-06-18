package com.rh.i_mes_plus.model.pdt;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 指令单号表
 *
 * @author hbq
 * @date 2021-05-27 11:20:48
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pdt_wms_pm_mo_base")
public class PdtWmsPmMoBase extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "生产阶别")
        @ApiModelProperty(value = "生产阶别")
        private String msCode;
        @Excel(name = "工单ID(PROJECT_BASE_ID)")
        @ApiModelProperty(value = "工单ID(PROJECT_BASE_ID)")
        private String projectId;
        @Excel(name = "客户名称")
        @ApiModelProperty(value = "客户名称")
        private String custCode;
        @Excel(name = "制令号")
        @ApiModelProperty(value = "制令号")
        private String moNo;
        @Excel(name = "机种名(生产料件)")
        @ApiModelProperty(value = "机种名(生产料件)")
        private String modelCode;
        @Excel(name = "品名")
        @ApiModelProperty(value = "品名")
        private String modelName;
        @Excel(name = "加工面(A单面 S 正面 C 反面)")
        @ApiModelProperty(value = "加工面(A单面 S 正面 C 反面)")
        private String scFlag;
        @Excel(name = "线别代码")
        @ApiModelProperty(value = "线别代码")
        private String lineCode;
        @Excel(name = "工艺ID")
        @ApiModelProperty(value = "工艺ID")
        private Long technicsId;
        @Excel(name = "预投产时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "预投产时间")
        private Date moScheduleDate;
        @Excel(name = "预关结时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "预关结时间")
        private Date moDueDate;
        @Excel(name = "联板数")
        @ApiModelProperty(value = "联板数")
        private String pcbCount;
        @Excel(name = "料站主表ID")
        @ApiModelProperty(value = "料站sn")
        private String fsSn;
        @Excel(name = "产出面标识（Y/N）")
        @ApiModelProperty(value = "产出面标识（Y/N）")
        private String masterFlag;
        @Excel(name = "工单投入阶（Y/N）")
        @ApiModelProperty(value = "工单投入阶（Y/N）")
        private String moInput;
        @Excel(name = "工单产出阶（Y/N）")
        @ApiModelProperty(value = "工单产出阶（Y/N）")
        private String moOutput;
        @Excel(name = "员工号")
        @ApiModelProperty(value = "员工号")
        private String createNo;
        @Excel(name = "实际投入时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "实际投入时间")
        private Date moStartDate;
        @Excel(name = "实际关结时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "实际关结时间")
        private Date moCloseDate;
        @Excel(name = "开立时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "开立时间")
        private Date moCreateDate;
        @Excel(name = "生产数量")
        @ApiModelProperty(value = "生产数量")
        private Long targetQty;
        @Excel(name = "已投入量")
        @ApiModelProperty(value = "已投入量")
        private Long inputQty;
        @Excel(name = "已产出量")
        @ApiModelProperty(value = "已产出量")
        private Long outputQty;
        @Excel(name = "回流数量")
        @ApiModelProperty(value = "回流数量")
        private Long turnOutQty;
        @Excel(name = "作废数量")
        @ApiModelProperty(value = "作废数量")
        private Long totalScrapQty;
        @Excel(name = "生产制令单状态(1:开立,2:上线,3:异常关结,4:投产,5:挂线,6:生产结束,7:离线)")
        @ApiModelProperty(value = "生产制令单状态(1:开立,2:上线,3:异常关结,4:投产,5:挂线,6:生产结束,7:离线)")
        private String closeFlag;
        @Excel(name = "起始工序")
        @ApiModelProperty(value = "起始工序")
        private String defaultGroup;
        @Excel(name = "最后一道工序")
        @ApiModelProperty(value = "最后一道工序")
        private String endGroup;
        @Excel(name = "贴片点数")
        @ApiModelProperty(value = "贴片点数")
        private Long tpNumber;
        @Excel(name = "成品管控类型1批次、2个体")
        @ApiModelProperty(value = "成品管控类型1批次、2个体")
        private Long productControlType;
        @Excel(name = "条码拼板数")
        @ApiModelProperty(value = "条码拼板数")
        private Long jigsawNumber;
        @Excel(name = "半成品机种")
        @ApiModelProperty(value = "半成品机种")
        private String halfModelCode;
        @Excel(name = "部门代码")
        @ApiModelProperty(value = "部门代码")
        private String depaCode;
        @Excel(name = "部门名称")
        @ApiModelProperty(value = "部门名称")
        private String depaName;
        @Excel(name = "备注")
        @ApiModelProperty(value = "备注")
        private String remark;

}
