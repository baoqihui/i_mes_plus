package com.rh.i_mes_plus.model.gtoa;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 不允许发货版本控制
 *
 * @author hbq
 * @date 2020-11-02 18:30:05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ver_control_delivery")
public class VerControlDelivery extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "机型")
        @ApiModelProperty(value = "机型")
        private String modelName;
        @Excel(name = "客户")
        @ApiModelProperty(value = "客户")
        private String customer;
        @Excel(name = "版本")
        @ApiModelProperty(value = "版本")
        private String ver;
        @Excel(name = "是否同意")
        @ApiModelProperty(value = "是否同意")
        private Boolean agreeFlag;
        @Excel(name = "禁发依据(文件初始为空)")
        @ApiModelProperty(value = "禁发依据(文件初始为空)")
        private String accordingForDisagree;
        @Excel(name = "")
        @ApiModelProperty(value = "")
        private String createName;
        @Excel(name = "",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "")
        private String updateName;
        @TableLogic
        @ApiModelProperty(value = "",hidden = true)
        private Boolean isDel;
}
