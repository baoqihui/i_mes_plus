package com.rh.i_mes_plus.model.gtoa;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 机型任务表（包含10文件以及当前执行所有文件）
 *
 * @author hbq
 * @date 2020-10-19 18:59:55
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pdt_standard_file_info")
public class PdtStandardFileInfo extends SuperEntity {
    private static final long serialVersionUID=1L;
        @Excel(name = "ECN")
        @ApiModelProperty(value = "ECN")
        private String ecnNo;
        @Excel(name = "机型")
        @ApiModelProperty(value = "机型")
        private String modelName;
        @Excel(name = "发行版本")
        @ApiModelProperty(value = "发行版本")
        private String ver;
        @Excel(name = "BOM")
        @ApiModelProperty(value = "BOM")
        private String bom;
        @Excel(name = "料站表")
        @ApiModelProperty(value = "料站表")
        private String feelder;
        @Excel(name = "作业指导")
        @ApiModelProperty(value = "作业指导")
        private String instruction;
        @Excel(name = "ECN编号")
        @ApiModelProperty(value = "ECN编号")
        private String ecn;
        @Excel(name = "规格书")
        @ApiModelProperty(value = "规格书")
        private String fai;
        @Excel(name = "MARK图")
        @ApiModelProperty(value = "MARK图")
        private String mark;
        @Excel(name = "Gerber")
        @ApiModelProperty(value = "Gerber")
        private String gerber;
        @Excel(name = "Place")
        @ApiModelProperty(value = "Place")
        private String place;
        @Excel(name = "DFM")
        @ApiModelProperty(value = "DFM")
        private String dfm;
        @Excel(name = "工艺指导")
        @ApiModelProperty(value = "工艺指导")
        private String guidance;
        @Excel(name = "预留")
        @ApiModelProperty(value = "重工（不显示）")
        private String rework;
        @Excel(name = "预留")
        @ApiModelProperty(value = "预留")
        private String file3;
        @Excel(name = "发行版本标识(0,已弃用 1,在用 2,即将引用)")
        @ApiModelProperty(value = "发行版本标识(0,已弃用 1,在用 2,即将引用)")
        private Integer validFlag;
        @TableLogic
        @ApiModelProperty(value = "状态 0 禁用 1 启用",hidden = true)
        private Boolean isDel;
}
