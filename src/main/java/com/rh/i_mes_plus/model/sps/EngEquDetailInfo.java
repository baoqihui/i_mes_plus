package com.rh.i_mes_plus.model.sps;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 工程设备详情信息表
 *
 * @author hbq
 * @date 2021-02-24 18:51:21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("eng_equ_detail_info")
public class EngEquDetailInfo extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "使用工厂")
        @ApiModelProperty(value = "使用工厂")
        private String factory;
        @Excel(name = "管理部门")
        @ApiModelProperty(value = "管理部门")
        private String deptCode;
        @Excel(name = "线体代码")
        @ApiModelProperty(value = "线体代码")
        private String lineCode;
        @Excel(name = "治具大类代码")
        @ApiModelProperty(value = "治具大类代码")
        private String typeCode;
        @Excel(name = "小类别代码")
        @ApiModelProperty(value = "小类别代码")
        private String itemTypeCode;
        @Excel(name = "设备ID（内部编号）")
        @ApiModelProperty(value = "设备ID（内部编号）")
        private String equNo;
        @Excel(name = "设备SN（外部）")
        @ApiModelProperty(value = "设备SN（外部）")
        private String equSn;
        @Excel(name = "制造商")
        @ApiModelProperty(value = "制造商")
        private String manufacturyCode;
        @Excel(name = "供应商")
        @ApiModelProperty(value = "供应商")
        private String supplierCode;
        @Excel(name = "进厂日期",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "进厂日期")
        private Date inDate;
        @Excel(name = "状态")
        @ApiModelProperty(value = "状态")
        private String state;
}
