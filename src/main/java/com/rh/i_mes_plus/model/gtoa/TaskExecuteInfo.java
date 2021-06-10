package com.rh.i_mes_plus.model.gtoa;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 任务执行状态
 *
 * @author hbq
 * @date 2020-10-29 18:10:24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("task_execute_info")
public class TaskExecuteInfo extends SuperEntity {
    private static final long serialVersionUID=1L;

    @Excel(name = "ecn编号")
    @ApiModelProperty(value = "ecn编号")
    private String ecnNo;
    @Excel(name = "任务id")
    @ApiModelProperty(value = "任务id")
    private Long taskId;
    @Excel(name = "创建人")
    @ApiModelProperty(value = "创建人")
    private String createName;
    @Excel(name = "执行人账号")
    @ApiModelProperty(value = "执行人账号")
    private String executeAccount;
    @Excel(name = "执行人")
    @ApiModelProperty(value = "执行人")
    private String executeName;
    @Excel(name = "执行时间",format="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "执行时间")
    private Date executeTime;
    @Excel(name = "执行文件路径")
    @ApiModelProperty(value = "执行文件路径")
    private String executeFile;
    @Excel(name = "审核人")
    @ApiModelProperty(value = "审核人")
    private String auditName;
    @Excel(name = "审核时间",format="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "审核时间")
    private Date auditTime;
    @Excel(name = "审核原因")
    @ApiModelProperty(value = "审核原因")
    private String auditRemark;
    @Excel(name = "状态（0，新建/1，执行完成,2，审核通过）")
    @ApiModelProperty(value = "状态（0，新建/1，执行完成,2，审核通过）")
    private Integer state;
    @Excel(name = "任务所属部门")
    @ApiModelProperty(value = "任务所属部门")
    private String depaCode;
    @Excel(name = "任务所属部门名")
    @ApiModelProperty(value = "任务所属部门名")
    private String depaName;
}
