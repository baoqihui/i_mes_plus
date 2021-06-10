package com.rh.i_mes_plus.model.ums;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 物料类型
 *
 * @author hqb
 * @date 2020-09-23 17:16:07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_co_itemsource")
public class UmsCoItemsource extends SuperEntity {
    private static final long serialVersionUID=1L;
        @Excel(name = "关键物料类型代码")
        @ApiModelProperty(value = "关键物料类型代码")
        private String itemSourceCode;
        @Excel(name = "物料类型名称")
        @ApiModelProperty(value = "物料类型名称")
        private String itemSourcename;
        @Excel(name = "父物料类别")
        @ApiModelProperty(value = "父物料类别")
        private String itemSourcecodeFa;
        @Excel(name = "类别路径")
        @ApiModelProperty(value = "类别路径")
        private String itemPath;
        @Excel(name = "条码类型")
        @ApiModelProperty(value = "条码类型")
        private String snRuleType;
        @TableLogic
        @ApiModelProperty(value = "",hidden = true)
        private Boolean isDel;
        @Excel(name = "部门代码")
        @ApiModelProperty(value = "部门代码")
        private String depaCode;
        @Excel(name = "部门名称")
        @ApiModelProperty(value = "部门名称")
        private String depaName;
}
