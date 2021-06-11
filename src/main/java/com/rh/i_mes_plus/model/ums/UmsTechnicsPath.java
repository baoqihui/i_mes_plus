package com.rh.i_mes_plus.model.ums;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工艺路线
 *
 * @author hbq
 * @date 2021-06-01 10:57:04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_technics_path")
public class UmsTechnicsPath extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "工艺id")
        @ApiModelProperty(value = "工艺id")
        private Long utId;
        @Excel(name = "工作中心代码")
        @ApiModelProperty(value = "工作中心代码")
        private String wcCode;
        @Excel(name = "工序代码")
        @ApiModelProperty(value = "工序代码")
        private String processCode;
        @Excel(name = "工序名称")
        @ApiModelProperty(value = "工序名称")
        private String processName;
}
