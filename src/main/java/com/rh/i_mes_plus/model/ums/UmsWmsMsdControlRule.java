package com.rh.i_mes_plus.model.ums;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * MSD管控规则
 *
 * @author hqb
 * @date 2020-09-23 17:16:07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_wms_msd_control_rule")
public class UmsWmsMsdControlRule extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "MSD作业规则名称")
        @ApiModelProperty(value = "MSD作业规则名称")
        private String mcrName;
        @Excel(name = "等级")
        @ApiModelProperty(value = "等级")
        private String mcrGrade;
        @Excel(name = "使用时效(小时)")
        @ApiModelProperty(value = "使用时效(小时)")
        private Long mcrAging;
        @Excel(name = "开封限时(小时)")
        @ApiModelProperty(value = "开封限时(小时)")
        private Long mcrOpenLimit;
        @Excel(name = "密封限时(月)")
        @ApiModelProperty(value = "密封限时(月)")
        private Long mcrSealLimit;
        @Excel(name = "MSDCODE")
        @ApiModelProperty(value = "MSDCODE")
        private String mcrMsdCode;
        @Excel(name = "最大烘烤次数")
        @ApiModelProperty(value = "最大烘烤次数")
        private Long mcrDrytimes;
        @Excel(name = "拆封立即烘烤（Y/N）")
        @ApiModelProperty(value = "拆封立即烘烤（Y/N）")
        private String mcrOpenDry;
        @Excel(name = "暴露临界值(入干燥箱)")
        @ApiModelProperty(value = "暴露临界值(入干燥箱)")
        private Long mcrIndrycabLimit;
        @Excel(name = "车间寿命(使用时效)")
        @ApiModelProperty(value = "车间寿命(使用时效)")
        private Long mcrFloorLife;
        @Excel(name = "默认标识(Y是N否)")
        @ApiModelProperty(value = "默认标识(Y是N否)")
        private String mcrFlag;
        @Excel(name = "累计最大烘烤时长(小时)")
        @ApiModelProperty(value = "累计最大烘烤时长(小时)")
        private Long mcrHours;
        @TableLogic
        @ApiModelProperty(value = "",hidden = true)
        private Boolean isDel;
        @Excel(name = "部门代码")
        @ApiModelProperty(value = "部门代码")
        private String depaCode;
        @Excel(name = "部门名称")
        @ApiModelProperty(value = "部门名称")
        private String depaName;
}
