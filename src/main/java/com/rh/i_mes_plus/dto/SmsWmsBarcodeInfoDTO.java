package com.rh.i_mes_plus.dto;

import com.rh.i_mes_plus.model.sms.SmsWmsBarcodeInfo;
import lombok.Data;

@Data
public class SmsWmsBarcodeInfoDTO extends SmsWmsBarcodeInfo{
    //打印数目
    private Integer num;
    //送货数量
    private Long deliveryNum;
    //最小包装数
    private Long itemMinPack;
}
