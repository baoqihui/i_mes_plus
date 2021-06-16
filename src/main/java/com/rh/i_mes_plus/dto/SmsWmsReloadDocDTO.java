package com.rh.i_mes_plus.dto;

import com.rh.i_mes_plus.model.sms.SmsWmsReloadDoc;
import com.rh.i_mes_plus.model.sms.SmsWmsReloadDocDetail;
import lombok.Data;

import java.util.List;

@Data
public class SmsWmsReloadDocDTO extends SmsWmsReloadDoc {
    private List<SmsWmsReloadDocDetail> smsWmsReloadDocDetails;
}
