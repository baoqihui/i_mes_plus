package com.rh.i_mes_plus.model.ums;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 料枪规格
 *
 * @author hbq
 * @date 2021-05-21 15:02:34
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_feeder_spec")
public class UmsFeederSpec extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "类型id")
        @ApiModelProperty(value = "类型id")
        private Long typeId;
        @Excel(name = "规格代码")
        @ApiModelProperty(value = "规格代码")
        private String specCode;
        @Excel(name = "规格名称")
        @ApiModelProperty(value = "规格名称")
        private String specName;
        @Excel(name = "备注")
        @ApiModelProperty(value = "备注")
        private String remark;
        @Excel(name = "创建人")
        @ApiModelProperty(value = "创建人")
        private String createName;
}
