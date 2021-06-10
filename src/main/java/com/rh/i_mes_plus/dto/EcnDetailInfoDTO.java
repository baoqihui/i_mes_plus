package com.rh.i_mes_plus.dto;

import com.rh.i_mes_plus.model.gtoa.EcnDetailDiscriptionInfo;
import com.rh.i_mes_plus.model.gtoa.EcnDetailInfo;
import lombok.Data;

import java.util.List;

@Data
public class EcnDetailInfoDTO extends EcnDetailInfo {
    private List<EcnDetailDiscriptionInfo> ecnDetailDiscriptionInfos;
}
