package com.rh.i_mes_plus.dto;

import com.rh.i_mes_plus.model.sms.SmsWmsBarcodeInfo;
import com.rh.i_mes_plus.model.sms.SmsWmsReceiveDetail;
import com.rh.i_mes_plus.model.sms.SmsWmsReceiveDoc;
import lombok.Data;

import java.util.List;

/**
 *  采购入库单dto
 */
@Data
public class SmsWmsReceiveAllDTO {
    //入库单
    private SmsWmsReceiveDoc smsWmsReceiveDoc;
    //入库单详情集合
    private List<SmsWmsReceiveDetail> smsWmsReceiveDetails;
    //所打印条码集合
    private List<SmsWmsBarcodeInfo> smsWmsBarcodeInfos;
}
