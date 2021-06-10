package com.rh.i_mes_plus.model.gtoa;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 *
 * @author hbq
 * @date 2020-10-21 19:50:27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("email_config")
public class EmailConfig extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "")
        @ApiModelProperty(value = "")
        private String title;
        @Excel(name = "前缀（提示语）")
        @ApiModelProperty(value = "前缀（提示语）")
        private String prefix;
        @Excel(name = "后缀（文件路径）")
        @ApiModelProperty(value = "后缀（文件路径）")
        private String suffix;
        @Excel(name = "禁用标识（0，禁用 1，启用）")
        @ApiModelProperty(value = "禁用标识（0，禁用 1，启用）")
        private Boolean status;
}
