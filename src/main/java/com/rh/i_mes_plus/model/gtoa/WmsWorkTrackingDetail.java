package com.rh.i_mes_plus.model.gtoa;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 任务详情
 *
 * @author hbq
 * @date 2020-12-02 15:59:56
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wms_work_tracking_detail")
public class WmsWorkTrackingDetail extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "work表主键")
        @ApiModelProperty(value = "work表主键")
        private Integer workId;
        @Excel(name = "更新内容")
        @ApiModelProperty(value = "更新内容")
        private String content;
        @Excel(name = "更新时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "更新时间")
        private Date contentTime;
        @Excel(name = "更新人")
        @ApiModelProperty(value = "更新人")
        private String contentUsr;
}
