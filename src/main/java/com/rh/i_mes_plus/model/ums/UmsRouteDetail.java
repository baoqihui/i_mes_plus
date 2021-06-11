package com.rh.i_mes_plus.model.ums;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程控制详情
 *
 * @author hbq
 * @date 2021-06-01 09:33:59
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_route_detail")
public class UmsRouteDetail extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "流程id")
        @ApiModelProperty(value = "流程id")
        private Long urId;
        @Excel(name = "工序代码")
        @ApiModelProperty(value = "工序代码")
        private String processCode;
        @Excel(name = "上一个流程工序id")
        @ApiModelProperty(value = "上一个流程工序id")
        private Long preUrdId;
        @Excel(name = "状态: （0，正常；1，维修；2，回流 ）")
        @ApiModelProperty(value = "状态: （0，正常；1，维修；2，回流 ）")
        private Integer state;
        @Excel(name = "是否必过（0，非必过 1，必过）")
        @ApiModelProperty(value = "是否必过（0，非必过 1，必过）")
        private Integer isMustPass;
        @Excel(name = "排序")
        @ApiModelProperty(value = "排序")
        private Integer sort;
}
