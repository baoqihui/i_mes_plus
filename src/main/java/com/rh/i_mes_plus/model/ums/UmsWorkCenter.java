package com.rh.i_mes_plus.model.ums;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工作中心
 *
 * @author hbq
 * @date 2021-05-20 15:31:14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_work_center")
public class UmsWorkCenter extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "工作重心代码")
        @ApiModelProperty(value = "工作重心代码")
        private String wcCode;
        @Excel(name = "工作重心名称")
        @ApiModelProperty(value = "工作重心名称")
        private String wcName;
        @Excel(name = "线别代码")
        @ApiModelProperty(value = "线别代码")
        private String lineCode;
        @Excel(name = "工序代码")
        @ApiModelProperty(value = "工序代码")
        private String processCode;
        @Excel(name = "终端类型（1，DCT 2，PDA 3，PC）")
        @ApiModelProperty(value = "终端类型（1，DCT 2，PDA 3，PC）")
        private Integer terminalType;
        @Excel(name = "工种（1，测试员 2，维修员 3，STM线长）")
        @ApiModelProperty(value = "工种（1，测试员 2，维修员 3，STM线长）")
        private Integer craft;
        @Excel(name = "是否启用（0，禁用 1，启用）")
        @ApiModelProperty(value = "是否启用（0，禁用 1，启用）")
        private Integer isValue;
        @Excel(name = "创建人")
        @ApiModelProperty(value = "创建人")
        private String createName;
        @Excel(name = "更新人")
        @ApiModelProperty(value = "更新人")
        private String updateName;
}
