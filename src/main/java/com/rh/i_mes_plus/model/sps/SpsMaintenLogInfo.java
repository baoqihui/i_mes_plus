package com.rh.i_mes_plus.model.sps;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 保养记录信息表
 *
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sps_mainten_log_info")
public class SpsMaintenLogInfo extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "类别")
        @ApiModelProperty(value = "类别")
        private String typeCode;
        @Excel(name = "小类代码")
        @ApiModelProperty(value = "小类代码")
        private String itemTypeCode;
        @Excel(name = "备具号")
        @ApiModelProperty(value = "备具号")
        private String sparesNo;
        @Excel(name = "保养周期代码")
        @ApiModelProperty(value = "保养周期代码")
        private String cycleCode;
        @Excel(name = "保养内容名集合")
        @ApiModelProperty(value = "保养内容名集合")
        private String commentNames;
        @Excel(name = "保养内容代码集合")
        @ApiModelProperty(value = "保养内容代码集合")
        private String commentCodes;
        @Excel(name = "保养人")
        @ApiModelProperty(value = "保养人")
        private String maintener;
        @Excel(name = "保养时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "保养时间")
        private Date maintenTime;
        @Excel(name = "借用单号")
        @ApiModelProperty(value = "借用单号")
        private String reqNo;
        @Excel(name = "保养状态（1.未保养 2. 已保养）")
        @ApiModelProperty(value = "保养状态（1.未保养 2. 已保养）")
        private String state;
}
