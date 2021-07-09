package com.rh.i_mes_plus.model.sps;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import com.rh.i_mes_plus.common.model.SuperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 红锡膏退仓记录
 *
 * @author hbq
 * @date 2021-07-08 19:48:01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tin_return_record")
public class TinReturnRecord extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "领用主键")
        @ApiModelProperty(value = "领用主键")
        private Long useId;
        @Excel(name = "红锡膏SN")
        @ApiModelProperty(value = "红锡膏SN")
        private String tinSn;
        @Excel(name = "退仓时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "退仓时间")
        private Date returnTime;
        @Excel(name = "退仓人")
        @ApiModelProperty(value = "退仓人")
        private String returnName;
        @Excel(name = "区域SN")
        @ApiModelProperty(value = "区域SN")
        private String areaSn;
}
