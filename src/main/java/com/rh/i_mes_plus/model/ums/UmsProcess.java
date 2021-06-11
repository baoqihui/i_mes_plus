package com.rh.i_mes_plus.model.ums;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工序管理
 *
 * @author hbq
 * @date 2021-05-20 15:31:14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_process")
public class UmsProcess extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "工序代码")
        @ApiModelProperty(value = "工序代码")
        private String processCode;
        @Excel(name = "工序名称")
        @ApiModelProperty(value = "工序名称")
        private String processName;
        @Excel(name = "生产阶别")
        @ApiModelProperty(value = "生产阶别")
        private String msCode;
}
