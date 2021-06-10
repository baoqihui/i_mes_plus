package com.rh.i_mes_plus.model.gtoa;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * fal
 *
 * @author hbq
 * @date 2020-11-05 20:14:46
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("fai_info")
public class FaiInfo extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "ecn编号")
        @ApiModelProperty(value = "ecn编号")
        private String ecnNo;
        @Excel(name = "导入时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "导入时间")
        private Date ecnImportTime;
        @Excel(name = "发布时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "发布时间")
        private Date ecnReleaseTime;
        @Excel(name = "变更后版本")
        @ApiModelProperty(value = "变更后版本")
        private String pcbaVer;
        @Excel(name = "ecn目的")
        @ApiModelProperty(value = "ecn目的")
        private String ecnPurpose;
        @Excel(name = "条码图片")
        @ApiModelProperty(value = "条码图片")
        private String picLable;
        @Excel(name = "正面图片")
        @ApiModelProperty(value = "正面图片")
        private String picTop;
        @Excel(name = "方面图片")
        @ApiModelProperty(value = "方面图片")
        private String picBot;
        @Excel(name = "3C或ROHS图片")
        @ApiModelProperty(value = "3C或ROHS图片")
        private String picCR;
        @Excel(name = "附加图片")
        @ApiModelProperty(value = "附加图片")
        private String picsAdd;
        @Excel(name = "生产治具、工艺流程是否需要变更")
        @ApiModelProperty(value = "生产治具、工艺流程是否需要变更")
        private Boolean mfgChanageFlag;
        @Excel(name = "btf测试程序是否变更")
        @ApiModelProperty(value = "btf测试程序是否变更")
        private Boolean btfChanageFlag;
        @Excel(name = "创建人")
        @ApiModelProperty(value = "创建人")
        private String createName;
        @Excel(name = "审核人")
        @ApiModelProperty(value = "审核人")
        private String auditName;
        @Excel(name = "审核时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "审核时间")
        private Date auditTime;
        @Excel(name = "审核状态")
        @ApiModelProperty(value = "审核状态")
        private Integer auditState;

    public FaiInfo() {
    }

    public FaiInfo(String ecnNo) {
        this.ecnNo = ecnNo;
    }
}
