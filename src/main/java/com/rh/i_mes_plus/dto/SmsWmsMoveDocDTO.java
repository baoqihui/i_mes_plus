package com.rh.i_mes_plus.dto;

import com.rh.i_mes_plus.model.sms.SmsWmsMoveDoc;
import com.rh.i_mes_plus.model.sms.SmsWmsMoveDocDetail;
import lombok.Data;

import java.util.List;

@Data
public class SmsWmsMoveDocDTO extends SmsWmsMoveDoc {
    private List<SmsWmsMoveDocDetail> smsWmsMoveDocDetails;
}
