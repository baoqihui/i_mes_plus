package com.rh.i_mes_plus.dto;

import com.rh.i_mes_plus.model.sps.SpsEngFixReqDetailInfo;
import com.rh.i_mes_plus.model.sps.SpsEngFixReqInfo;
import lombok.Data;

import java.util.List;

@Data
public class SpsEngFixReqInfoDTO extends SpsEngFixReqInfo {
    private List<SpsEngFixReqDetailInfo> spsEngFixReqDetailInfos;
}
