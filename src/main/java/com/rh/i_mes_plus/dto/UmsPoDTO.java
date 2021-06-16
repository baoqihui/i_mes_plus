package com.rh.i_mes_plus.dto;

import com.rh.i_mes_plus.model.sms.SmsWmsPo;
import com.rh.i_mes_plus.model.sms.SmsWmsPoDet;
import lombok.Data;

import java.util.List;

/**
 * 采购单dto
 * smsWmsPo 单个采购单
 * smsWmsPoDets 采购单详情
 */
@Data
public class UmsPoDTO {
    private static final long serialVersionUID=1L;
    //单个采购单
    private SmsWmsPo smsWmsPo;
    //采购单详情
    private List<SmsWmsPoDet> smsWmsPoDets;

}
