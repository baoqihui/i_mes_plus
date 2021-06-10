package com.rh.i_mes_plus.model.ums;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 辅料储位
 *
 * @author hqb
 * @date 2020-09-23 17:16:06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_acce_area")
public class UmsAcceArea extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "区域SN")
        @ApiModelProperty(value = "区域SN")
        private String arSn;
        @Excel(name = "区域名称")
        @ApiModelProperty(value = "区域名称")
        private String arName;
        @Excel(name = "")
        @ApiModelProperty(value = "")
        private String commandsetId;
        @Excel(name = "仓库类别代码")
        @ApiModelProperty(value = "仓库类别代码")
        private String stCode;
        @Excel(name = "区域类别(S 仓库 A 库区 P 库位 )")
        @ApiModelProperty(value = "区域类别(S 仓库 A 库区 P 库位 )")
        private String atCode;
        @Excel(name = "设备类型(PC  PDA  DCT)")
        @ApiModelProperty(value = "设备类型(PC  PDA  DCT)")
        private String arDevice;
        @Excel(name = "备注")
        @ApiModelProperty(value = "备注")
        private String arRemark;
        @Excel(name = "面积")
        @ApiModelProperty(value = "面积")
        private Long arArea;
        @Excel(name = "面积单位ID")
        @ApiModelProperty(value = "面积单位ID")
        private Long arAreaUnit;
        @Excel(name = "容积")
        @ApiModelProperty(value = "容积")
        private Long arCubage;
        @Excel(name = "容积单位ID")
        @ApiModelProperty(value = "容积单位ID")
        private Long arCubageUnit;
        @Excel(name = "可出库标志")
        @ApiModelProperty(value = "可出库标志")
        private String arOutFlag;
        @Excel(name = "可入库标志")
        @ApiModelProperty(value = "可入库标志")
        private String arInFlag;
        @Excel(name = "路径")
        @ApiModelProperty(value = "路径")
        private String arPath;
        @Excel(name = "状态(N启用,Y禁用)")
        @ApiModelProperty(value = "状态(N启用,Y禁用)")
        private String arState;
        @Excel(name = "隶属区域")
        @ApiModelProperty(value = "隶属区域")
        private String arFatherSn;
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
