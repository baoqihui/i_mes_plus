package com.rh.i_mes_plus.model.gtoa;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * ecn变更描述
 *
 * @author hbq
 * @date 2020-11-05 20:14:47
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ecn_detail_discription_info")
public class EcnDetailDiscriptionInfo extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "ecn详情主键")
        @ApiModelProperty(value = "ecn详情主键")
        private Long ediId;
        @Excel(name = "项目id")
        @ApiModelProperty(value = "项目id")
        private Long itemId;
        @Excel(name = "变更项目名称")
        @ApiModelProperty(value = "变更项目名称")
        private String itemName;
        @Excel(name = "变更前内容")
        @ApiModelProperty(value = "变更前内容")
        private String beforeChange;
        @Excel(name = "变更后内容")
        @ApiModelProperty(value = "变更后内容")
        private String afterChange;
        @Excel(name = "有效标识(是否已执行)")
        @ApiModelProperty(value = "有效标识(是否已执行)")
        private Boolean validFlag;
        @Excel(name = "导入日期",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "导入日期")
        private Date dateIntroduct;
        @Excel(name = "导入工单")
        @ApiModelProperty(value = "导入工单")
        private String introductMo;
        @Excel(name = "确认人")
        @ApiModelProperty(value = "确认人")
        private String usrVerify;
        @Excel(name = "执行人")
        @ApiModelProperty(value = "执行人")
        private String usrExec;
        @Excel(name = "涉及区域")
        @ApiModelProperty(value = "涉及区域")
        private String coverArea;
        @Excel(name = "备注")
        @ApiModelProperty(value = "备注")
        private String remark;
        @Excel(name = "是否为草稿")
        @ApiModelProperty(value = "是否为草稿")
        private Boolean isDraft;
        @Excel(name = "是否可删除")
        @ApiModelProperty(value = "是否可删除")
        private Boolean canDel;
}
