package com.rh.i_mes_plus.model.sps;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程控制
 *
 * @author hbq
 * @date 2021-02-21 16:06:24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("approval_process")
public class ApprovalProcess extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "大类代码")
        @ApiModelProperty(value = "大类代码")
        private String typeCode;
        @Excel(name = "操作类型（1.报废）")
        @ApiModelProperty(value = "操作类型（1.报废）")
        private String operType;
        @Excel(name = "流编号")
        @ApiModelProperty(value = "流编号")
        private Long streamNo;
        @Excel(name = "节点编号")
        @ApiModelProperty(value = "节点编号")
        private String nodeNo;
        @Excel(name = "节点名称")
        @ApiModelProperty(value = "节点名称")
        private String nodeName;
        @Excel(name = "是否自动审核（0，非自动 1，自动）")
        @ApiModelProperty(value = "是否自动审核（0，非自动 1，自动）")
        private Boolean isAutoPass;
        @Excel(name = "操作人编号")
        @ApiModelProperty(value = "操作人编号")
        private String operUsrNo;
}
