package com.rh.i_mes_plus.model.gtoa;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 
 *
 * @author hbq
 * @date 2020-12-02 15:59:56
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wms_work_tracing_main")
public class WmsWorkTracingMain extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "任务名称")
        @ApiModelProperty(value = "任务名称")
        private String name;
        @Excel(name = "任务描述")
        @ApiModelProperty(value = "任务描述")
        private String workDesc;
        @Excel(name = "发起原因")
        @ApiModelProperty(value = "发起原因")
        private String reason;
        @Excel(name = "预期结果")
        @ApiModelProperty(value = "预期结果")
        private String target;
        @Excel(name = "发起人")
        @ApiModelProperty(value = "发起人")
        private String sponsor;
        @Excel(name = "发起日期",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "发起日期")
        private Date sponseDate;
        @Excel(name = "发起部门")
        @ApiModelProperty(value = "发起部门")
        private String sponseDept;
        @Excel(name = "执行人")
        @ApiModelProperty(value = "执行人")
        private String excutor;
        @Excel(name = "执行部门")
        @ApiModelProperty(value = "执行部门")
        private String excuteDept;
        @Excel(name = "预计完成时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "预计完成时间")
        private Date expectDate;
        @Excel(name = "实际完成时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "实际完成时间")
        private Date realFinishDate;
        @Excel(name = "结案描述")
        @ApiModelProperty(value = "结案描述")
        private String result;
        @Excel(name = "状态(1: 新建 2: 进行中 3: 关结申请中4: 已关结 5: 超期未完成)")
        @ApiModelProperty(value = "状态(1: 新建 2: 进行中 3: 关结申请中4: 已关结 5: 超期未完成)")
        private String closeFlag;
}
