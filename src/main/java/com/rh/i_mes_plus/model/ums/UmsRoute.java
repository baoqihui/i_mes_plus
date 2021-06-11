package com.rh.i_mes_plus.model.ums;

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
 * @date 2021-06-01 09:33:59
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_route")
public class UmsRoute extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "流程名称")
        @ApiModelProperty(value = "流程名称")
        private String routeName;
        @Excel(name = "流程描述")
        @ApiModelProperty(value = "流程描述")
        private String routeDesc;
        @Excel(name = "生产阶别")
        @ApiModelProperty(value = "生产阶别")
        private String msCode;
        @Excel(name = "是否生效")
        @ApiModelProperty(value = "是否生效")
        private Integer isValue;
        @Excel(name = "创建人")
        @ApiModelProperty(value = "创建人")
        private String createName;
        @Excel(name = "更新人")
        @ApiModelProperty(value = "更新人")
        private String updateName;
}
