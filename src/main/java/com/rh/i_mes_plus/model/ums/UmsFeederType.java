package com.rh.i_mes_plus.model.ums;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 料枪类型
 *
 * @author hbq
 * @date 2021-05-21 15:02:34
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_feeder_type")
public class UmsFeederType extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "分类名")
        @ApiModelProperty(value = "分类名")
        private String typeName;
        @Excel(name = "备注")
        @ApiModelProperty(value = "备注")
        private String typeRemark;
}
