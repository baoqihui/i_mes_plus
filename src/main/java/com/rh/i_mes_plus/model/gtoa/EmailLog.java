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
 * @date 2020-10-21 19:42:58
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("email_log")
public class EmailLog extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "接收人")
        @ApiModelProperty(value = "接收人")
        private String recipient;
        @Excel(name = "标题")
        @ApiModelProperty(value = "标题")
        private String title;
        @Excel(name = "内容")
        @ApiModelProperty(value = "内容")
        private String context;
}
