package com.rh.i_mes_plus.model.ums;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 备品大类信息表
 *
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_spares_type_info")
public class UmsSparesTypeInfo extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "类别代码")
        @ApiModelProperty(value = "类别代码")
        private String typeCode;
        @Excel(name = "备品类型：1. 工程治具；2. 工程设备； 3. 工装治具； 4. 工装备件； 5. 钢网； 6. 其他")
        @ApiModelProperty(value = "备品类型：1. 工程治具；2. 工程设备； 3. 工装治具； 4. 工装备件； 5. 钢网； 6. 其他")
        private String typeName;
        @Excel(name = "部门")
        @ApiModelProperty(value = "部门")
        private String depaCode;

}
