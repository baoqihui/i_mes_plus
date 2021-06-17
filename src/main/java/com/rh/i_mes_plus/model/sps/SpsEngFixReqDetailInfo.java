package com.rh.i_mes_plus.model.sps;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工程治具借出详情表
 *
 * @author hbq
 * @date 2021-02-20 09:14:10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sps_eng_fix_req_detail_info")
public class SpsEngFixReqDetailInfo extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "借用申请单号")
        @ApiModelProperty(value = "借用申请单号")
        private String reqNo;
        @Excel(name = "治具编号")
        @ApiModelProperty(value = "治具编号")
        private String fixNo;
        @Excel(name = "是否已归还(1.未归还 2. 已归还)")
        @ApiModelProperty(value = "是否已归还(1.未归还 2. 已归还)")
        private String hasReq;
        @Excel(name = "使用次数")
        @ApiModelProperty(value = "使用次数")
        private Long useTimes;
}
