package com.rh.i_mes_plus.model.sps;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import com.rh.i_mes_plus.common.model.SuperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 辅料类型
 *
 * @author hbq
 * @date 2021-07-08 19:48:01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("assistant_tool_type")
public class AssistantToolType extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "类型代码")
        @ApiModelProperty(value = "类型代码")
        private String typeCode;
        @Excel(name = "类型名")
        @ApiModelProperty(value = "类型名")
        private String typeName;
        @Excel(name = "类型标识（0：助焊剂 3：钢网 4：刮刀 5：配件）")
        @ApiModelProperty(value = "类型标识（0：助焊剂 3：钢网 4：刮刀 5：配件）")
        private Integer typeFlag;
}
