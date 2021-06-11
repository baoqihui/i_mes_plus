package com.rh.i_mes_plus.model.ums;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 *
 * @author hqb
 * @date 2020-09-24 13:27:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_wms_area")
public class UmsWmsArea extends SuperEntity {
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
        @Excel(name = "东贝特有，其他项目不使用")
        @ApiModelProperty(value = "东贝特有，其他项目不使用")
        private String stCode;
        @Excel(name = "仓库、库区、库位区分标识（S:仓库、A:库区、P:库位）")
        @ApiModelProperty(value = "仓库、库区、库位区分标识（S:仓库、A:库区、P:库位）")
        private String atCode;
        @Excel(name = "")
        @ApiModelProperty(value = "")
        private String arDevice;
        @Excel(name = "备注")
        @ApiModelProperty(value = "备注")
        private String arRemark;
        @Excel(name = "")
        @ApiModelProperty(value = "")
        private Long arArea;
        @Excel(name = "")
        @ApiModelProperty(value = "")
        private Long arAreaUnit;
        @Excel(name = "")
        @ApiModelProperty(value = "")
        private Long arCubage;
        @Excel(name = "")
        @ApiModelProperty(value = "")
        private Long arCubageUnit;
        @Excel(name = "")
        @ApiModelProperty(value = "")
        private String arOutFlag;
        @Excel(name = "")
        @ApiModelProperty(value = "")
        private String arInFlag;
        @Excel(name = "路径")
        @ApiModelProperty(value = "路径")
        private String arPath;
        @Excel(name = "状态（N禁用，Y）")
        @ApiModelProperty(value = "状态（N禁用，Y）")
        private String arState;
        @Excel(name = "隶属区域")
        @ApiModelProperty(value = "隶属区域")
        private String arFatherSn;
        @Excel(name = "存储规则")
        @ApiModelProperty(value = "存储规则")
        private Long storageRules;
        @Excel(name = "存放箱数")
        @ApiModelProperty(value = "存放箱数")
        private Long storageBox;
        @Excel(name = "限存箱数")
        @ApiModelProperty(value = "限存箱数")
        private Long existingBox;
        @Excel(name = "是否老化仓位")
        @ApiModelProperty(value = "是否老化仓位")
        private String arIsAging;
        @Excel(name = "")
        @ApiModelProperty(value = "")
        private String areaProperty;
        @Excel(name = "是否为线边仓")
        @ApiModelProperty(value = "是否为线边仓")
        private String arIsLinearea;
        @Excel(name = "货架硬体编码")
        @ApiModelProperty(value = "货架硬体编码")
        private String hwAddr;
        @Excel(name = "货架服务IP")
        @ApiModelProperty(value = "货架服务IP")
        private String reelShelfIp;
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
