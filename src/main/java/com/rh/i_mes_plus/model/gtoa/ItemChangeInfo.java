package com.rh.i_mes_plus.model.gtoa;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 变更项目表
 *
 * @author hbq
 * @date 2020-10-22 19:50:24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("item_change_info")
public class ItemChangeInfo extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "英文名")
        @ApiModelProperty(value = "英文名")
        private String descEng;
        @Excel(name = "中文名")
        @ApiModelProperty(value = "中文名")
        private String descChn;
}
