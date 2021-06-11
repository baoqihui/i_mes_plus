package com.rh.i_mes_plus.model.ums;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rh.i_mes_plus.common.model.SuperEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户通知表,包含了所有用户的消息
 *
 * @author hqb
 * @date 2020-09-17 08:59:27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_notify")
public class UmsNotify extends SuperEntity {
    private static final long serialVersionUID=1L;

        @Excel(name = "发送者用户ID")
        @ApiModelProperty(value = "发送者用户ID")
        private Long senderId;
        @Excel(name = "发送者用户name")
        @ApiModelProperty(value = "发送者用户name")
        private String senderName;
        @Excel(name = "接受者用户ID")
        @ApiModelProperty(value = "接受者用户ID")
        private Long reciverId;
        @Excel(name = "消息类型:announcement公告/remind提醒/message私信")
        @ApiModelProperty(value = "消息类型:announcement公告/remind提醒/message私信")
        private Integer type;
        @Excel(name = "内容")
        @ApiModelProperty(value = "内容")
        private String content;
        @Excel(name = "是否已读,0未读,1已读")
        @ApiModelProperty(value = "是否已读,0未读,1已读")
        private Boolean isRead;
        @TableLogic
        @ApiModelProperty(value = "",hidden = true)
        private Boolean isDel;
}
