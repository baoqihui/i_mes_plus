package com.rh.i_mes_plus.model.sps;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 钢网借出详情表
 *
 * @author hbq
 * @date 2021-06-03 20:11:19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("mfg_stencils_req_info")
public class MfgStencilsReqInfo extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "治具编号")
        @ApiModelProperty(value = "治具编号")
        private String stencilNo;
        @Excel(name = "借用人")
        @ApiModelProperty(value = "借用人")
        private String lendUsr;
        @Excel(name = "借用时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "借用时间")
        private Date lendTime;
        @Excel(name = "借用时外观检测(0，ng 1，ok)")
        @ApiModelProperty(value = "借用时外观检测(0，ng 1，ok)")
        private Integer lendAppearanceInspection;
        @Excel(name = "借用时储位")
        @ApiModelProperty(value = "借用时储位")
        private String lendPos;
        @Excel(name = "借用时左上")
        @ApiModelProperty(value = "借用时左上")
        private Long lendLeftUpper;
        @Excel(name = "借用时右上")
        @ApiModelProperty(value = "借用时右上")
        private Long lendRightUpper;
        @Excel(name = "借用时左下")
        @ApiModelProperty(value = "借用时左下")
        private Long lendLeftLower;
        @Excel(name = "借用时右下")
        @ApiModelProperty(value = "借用时右下")
        private Long lendRightLower;
        @Excel(name = "借用时中间")
        @ApiModelProperty(value = "借用时中间")
        private Long lendCentre;
        @Excel(name = "归还人")
        @ApiModelProperty(value = "归还人")
        private String usrReturn;
        @Excel(name = "归还时间",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "归还时间")
        private Date timeReturn;
        @Excel(name = "归还时外观检测(0，ng 1，ok)")
        @ApiModelProperty(value = "归还时外观检测(0，ng 1，ok)")
        private Integer returnAppearanceInspection;
        @Excel(name = "归还时储位")
        @ApiModelProperty(value = "归还时储位")
        private String returnPos;
        @Excel(name = "归还时左上")
        @ApiModelProperty(value = "归还时左上")
        private Long returnLeftUpper;
        @Excel(name = "归还时右上")
        @ApiModelProperty(value = "归还时右上")
        private Long returnRightUpper;
        @Excel(name = "归还时左下")
        @ApiModelProperty(value = "归还时左下")
        private Long returnLeftLower;
        @Excel(name = "归还时右下")
        @ApiModelProperty(value = "归还时右下")
        private Long returnRightLower;
        @Excel(name = "归还时中间")
        @ApiModelProperty(value = "归还时中间")
        private Long returnCentre;
        @Excel(name = "本次使用次数")
        @ApiModelProperty(value = "本次使用次数")
        private Long thisUsedTimes;
        @Excel(name = "状态(1，新建 2，领用 3，归还)")
        @ApiModelProperty(value = "状态(1，新建 2，领用 3，归还)")
        private String state;
        @Excel(name = "线别代码")
        @ApiModelProperty(value = "线别代码")
        private String lineCode;
}
