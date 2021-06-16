package com.rh.i_mes_plus.dto;

import com.rh.i_mes_plus.model.sms.SmsWmsOutStockDetail;
import com.rh.i_mes_plus.model.sms.SmsWmsOutStockDoc;
import com.rh.i_mes_plus.model.sms.SmsWmsOutStockList;
import com.rh.i_mes_plus.model.sms.SmsWmsOutStockPmItem;
import lombok.Data;

import java.util.List;

@Data
public class SmsWmsOutStockDocDTO extends SmsWmsOutStockDoc {
    private List<SmsWmsOutStockDetail> smsWmsOutStockDetails;
    private List<SmsWmsOutStockPmItem> smsWmsOutStockPmItems;
    private List<SmsWmsOutStockList> smsWmsOutStockLists;
}
