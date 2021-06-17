package com.rh.i_mes_plus.model.sps;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 盘点明细表
 *
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sps_spares_check_detail")
public class SpsSparesCheckDetail extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "盘点单号",width = 30)
        @ApiModelProperty(value = "盘点单号")
        private String checkNo;
        @Excel(name = "备品号",width = 30)
        @ApiModelProperty(value = "备品号")
        private String sparesNo;
        @Excel(name = "盘点人")
        @ApiModelProperty(value = "盘点人")
        private String inventor;
        @Excel(name = "盘点时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "盘点时间")
        private Date timeInvent;
        @Excel(name = "状态",replace = { "未盘点_1", "已盘点_2"})
        @ApiModelProperty(value = "状态")
        private String state;
}
