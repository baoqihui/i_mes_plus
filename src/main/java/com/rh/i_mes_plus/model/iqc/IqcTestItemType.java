package com.rh.i_mes_plus.model.iqc;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 检测项目类型
 *
 * @author hqb
 * @date 2020-10-16 11:39:52
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("iqc_test_item_type")
public class IqcTestItemType extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "类型名称")
        @ApiModelProperty(value = "类型名称")
        private String name;
        @Excel(name = "备注")
        @ApiModelProperty(value = "备注")
        private String remark;
        @Excel(name = "部门代码")
        @ApiModelProperty(value = "部门代码")
        private String depaCode;
        @Excel(name = "部门名称")
        @ApiModelProperty(value = "部门名称")
        private String depaName;
}
