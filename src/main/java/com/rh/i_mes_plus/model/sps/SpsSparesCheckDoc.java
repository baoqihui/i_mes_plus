package com.rh.i_mes_plus.model.sps;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 盘点单信息表
 *
 * @author hbq
 * @date 2021-03-11 10:41:02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sps_spares_check_doc")
public class SpsSparesCheckDoc extends SuperEntity {
    private static final long serialVersionUID=1L;

    @Excel(name = "盘点单号")
    @ApiModelProperty(value = "盘点单号")
    private String checkNo;
    @Excel(name = "类别")
    @ApiModelProperty(value = "类别")
    private String typeCode;
    @Excel(name = "小类代码")
    @ApiModelProperty(value = "小类代码")
    private String itemTypeCode;
    @Excel(name = "客户代码")
    @ApiModelProperty(value = "客户代码")
    private String custCode;
    @Excel(name = "申请人")
    @ApiModelProperty(value = "申请人")
    private String applicant;
    @Excel(name = "申请时间",format="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "申请时间")
    private Date timeApp;
    @Excel(name = "部门经理")
    @ApiModelProperty(value = "部门经理")
    private String manager;
    @Excel(name = "部门经理审核时间",format="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "部门经理审核时间")
    private Date managerTimeReview;
    @Excel(name = "部门经理审核建议")
    @ApiModelProperty(value = "部门经理审核建议")
    private String managerComment;
    @Excel(name = "总经理")
    @ApiModelProperty(value = "总经理")
    private String president;
    @Excel(name = "总经理审核时间",format="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "总经理审核时间")
    private Date presidentTimeReview;
    @Excel(name = "总经理审核建议")
    @ApiModelProperty(value = "总经理审核建议")
    private String presidentComment;
    @Excel(name = "状态(0，新建 1，待审核 2，部门经理 ,3，总经理（审批完成）)")
    @ApiModelProperty(value = "状态(0，新建 1，待审核 2，部门经理 ,3，总经理（审批完成）)")
    private String state;
}
