package com.rh.i_mes_plus.model.gtoa;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 时间日志
 *
 * @author hbq
 * @date 2020-11-03 19:54:46
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("time_log")
public class TimeLog extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "")
        @ApiModelProperty(value = "")
        private String ecnNo;
        @Excel(name = "")
        @ApiModelProperty(value = "")
        private Integer status;
        @Excel(name = "所在步数名")
        @ApiModelProperty(value = "所在步数名")
        private String name;
        @Excel(name = "",format="yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "")
        private Date time;

    public TimeLog() {
    }

    public TimeLog(String ecnNo, Integer status) {
        this.ecnNo = ecnNo;
        this.status = status;
    }

    public TimeLog(String ecnNo, Integer status, String name) {
        this.ecnNo = ecnNo;
        this.status = status;
        this.name = name;
    }

    public TimeLog(String ecnNo, Integer status, String name, Date time) {
        this.ecnNo = ecnNo;
        this.status = status;
        this.name = name;
        this.time = time;
    }
}
