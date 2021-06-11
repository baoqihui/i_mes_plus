package com.rh.i_mes_plus.model.ums;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 线别管理
 *
 * @author hbq
 * @date 2021-05-20 15:31:14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_line_body")
public class UmsLineBody extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "线别代码")
        @ApiModelProperty(value = "线别代码")
        private String lineCode;
        @Excel(name = "线别名称")
        @ApiModelProperty(value = "线别名称")
        private String lineName;
        @Excel(name = "上级线别代码")
        @ApiModelProperty(value = "上级线别代码")
        private String parentLineCode;
        @Excel(name = "班别（1，白班 2，夜班）")
        @ApiModelProperty(value = "班别（1，白班 2，夜班）")
        private Integer classType;
        @Excel(name = "负责人")
        @ApiModelProperty(value = "负责人")
        private String principal;
        @Excel(name = "员工数")
        @ApiModelProperty(value = "员工数")
        private Integer staffNum;
        @Excel(name = "辅助人数")
        @ApiModelProperty(value = "辅助人数")
        private Integer assistNum;
        @Excel(name = "是否仓料管控")
        @ApiModelProperty(value = "是否仓料管控")
        private Integer isWarehouseControl;
        @Excel(name = "描述")
        @ApiModelProperty(value = "描述")
        private String remark;
}
