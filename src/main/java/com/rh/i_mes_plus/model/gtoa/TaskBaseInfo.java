package com.rh.i_mes_plus.model.gtoa;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 任务信息
 *
 * @author hbq
 * @date 2020-10-29 18:10:24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("task_base_info")
public class TaskBaseInfo extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "任务名")
        @ApiModelProperty(value = "任务名")
        private String name;
        @Excel(name = "描述（对应file_info表中的字段名，不可更改）")
        @ApiModelProperty(value = "描述（对应file_info表中的字段名，不可更改）")
        private String taskDesc;
        @Excel(name = "任务所属部门")
        @ApiModelProperty(value = "任务所属部门")
        private String depaCode;
        @Excel(name = "任务所属部门名")
        @ApiModelProperty(value = "任务所属部门名")
        private String depaName;
        @Excel(name = "是否需要文件")
        @ApiModelProperty(value = "是否需要文件")
        private Boolean hasFile;
}
