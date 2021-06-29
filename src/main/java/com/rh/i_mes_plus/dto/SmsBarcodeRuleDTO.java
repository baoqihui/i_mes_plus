package com.rh.i_mes_plus.dto;

import com.rh.i_mes_plus.model.sms.SmsBarcodeRule;
import com.rh.i_mes_plus.model.sms.SmsBarcodeRuleDetail;
import lombok.Data;

import java.util.List;

@Data
public class SmsBarcodeRuleDTO extends SmsBarcodeRule {
    private List<SmsBarcodeRuleDetail> smsBarcodeRuleDetails;
}
