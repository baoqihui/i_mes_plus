package com.rh.i_mes_plus.model.ums;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工艺
 *
 * @author hbq
 * @date 2021-06-01 10:57:04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_technics")
public class UmsTechnics extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "工艺名称")
        @ApiModelProperty(value = "工艺名称")
        private String technicsName;
        @Excel(name = "工艺类型: 1.正常；2.返修；3.样品")
        @ApiModelProperty(value = "工艺类型: 1.正常；2.返修；3.样品")
        private Integer technicsType;
        @Excel(name = "流程id")
        @ApiModelProperty(value = "流程id")
        private Long urId;
        @Excel(name = "前置工艺ID")
        @ApiModelProperty(value = "前置工艺ID")
        private Long preUtId;
        @Excel(name = "机种")
        @ApiModelProperty(value = "机种")
        private String modelCode;
        @Excel(name = "加工面(A单面 S 正面 C 反面)")
        @ApiModelProperty(value = "加工面(A单面 S 正面 C 反面)")
        private String scFlag;
        @Excel(name = "贴片点数")
        @ApiModelProperty(value = "贴片点数")
        private Long pointCount;
        @Excel(name = "版本")
        @ApiModelProperty(value = "版本")
        private String ver;
        @Excel(name = "阶别代码")
        @ApiModelProperty(value = "阶别代码")
        private String msCode;
        @Excel(name = "成品管控类型: 1.个体；2.批次")
        @ApiModelProperty(value = "成品管控类型: 1.个体；2.批次")
        private Integer controlType;
        @Excel(name = "是否默认")
        @ApiModelProperty(value = "是否默认")
        private Integer isDef;
        @Excel(name = "是否有效")
        @ApiModelProperty(value = "是否有效")
        private Integer isValue;
        @Excel(name = "创建人")
        @ApiModelProperty(value = "创建人")
        private String createName;
        @Excel(name = "编辑人")
        @ApiModelProperty(value = "编辑人")
        private String updateName;
}
