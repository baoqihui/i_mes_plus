package com.rh.i_mes_plus.model.sps;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 保养内容信息表
 *
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sps_mainten_comment_info")
public class SpsMaintenCommentInfo extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "保养周期代码")
        @ApiModelProperty(value = "保养周期代码")
        private String cycleCode;
        @Excel(name = "保养内容代码")
        @ApiModelProperty(value = "保养内容代码")
        private String commentCode;
        @Excel(name = "保养内容名称")
        @ApiModelProperty(value = "保养内容名称")
        private String commentName;
        @Excel(name = "保养内容描述")
        @ApiModelProperty(value = "保养内容描述")
        private String commentDesc;
        @Excel(name = "使用备品类别")
        @ApiModelProperty(value = "使用备品类别")
        private String typeCode;
        @Excel(name = "使用备品类别")
        @ApiModelProperty(value = "使用备品类别")
        private String itemTypeCode;
}
