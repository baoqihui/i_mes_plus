package com.rh.i_mes_plus.model.gtoa;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户文件
 *
 * @author hbq
 * @date 2020-10-19 18:59:55
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ecn_doc_info")
public class EcnDocInfo extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "ECN编号")
        @ApiModelProperty(value = "ECN编号")
        private String ecnNo;
        @Excel(name = "外部ECN编号")
        @ApiModelProperty(value = "外部ECN编号")
        private String ecnNoCust;
        @Excel(name = "客户名称")
        @ApiModelProperty(value = "客户名称")
        private String customer;
        @Excel(name = "适用机型")
        @ApiModelProperty(value = "适用机型")
        private String modelName;
        @Excel(name = "文件路径(前端用文件名作超链接)")
        @ApiModelProperty(value = "文件路径(前端用文件名作超链接)")
        private String filePath;
        @Excel(name = "创建人")
        @ApiModelProperty(value = "创建人")
        private String createName;
        @Excel(name = "更新人")
        @ApiModelProperty(value = "更新人")
        private String updateName;
        @Excel(name = "执行状态(1.新建  2.ECR审核（已提交未审核） 3.ECR审核（已通过，时间未完善） 4.ECR审核（未通过） 5.子任务执行中 6. QA验收（未验收） 7.QA验收(已通过)  8. QA验收（未通过）9.已导入)")
        @ApiModelProperty(value = "执行状态(1.新建  2.ECR审核（已提交未审核） 3.ECR审核（已通过，时间未完善） 4.ECR审核（未通过） 5.子任务执行中 6. QA验收（未验收） 7.QA验收(已通过)  8. QA验收（未通过）9.已导入)")
        private Integer exeState;
        @Excel(name = "快速关闭标识")
        @ApiModelProperty(value = "快速关闭标识")
        private Boolean quickCloseFlag;
        @Excel(name = "回退为真时可编辑（0,1）")
        @ApiModelProperty(value = "回退为真时可编辑（0,1）")
        private Boolean isRollBack;
        @TableLogic
        @ApiModelProperty(value = "",hidden = true)
        private Boolean isDel;
}
