package com.rh.i_mes_plus.model.ums;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门
 *
 * @author hqb
 * @date 2020-09-17 14:38:50
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_depa")
public class UmsDepa extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "代码")
        @ApiModelProperty(value = "代码")
        private String code;
        @Excel(name = "名称")
        @ApiModelProperty(value = "名称")
        private String name;
        @Excel(name = "地址")
        @ApiModelProperty(value = "地址")
        private String area;
        @Excel(name = "类型")
        @ApiModelProperty(value = "类型")
        private Integer type;
        @Excel(name = "备注")
        @ApiModelProperty(value = "备注")
        private String remark;
        @Excel(name = "父级部门code")
        @ApiModelProperty(value = "父级部门code")
        private String parentCode;
}
