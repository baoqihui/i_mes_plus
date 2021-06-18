package com.rh.i_mes_plus.dto;

import com.rh.i_mes_plus.model.pdt.PdtFeedingStation;
import com.rh.i_mes_plus.model.pdt.PdtFeedingStationDetail;
import lombok.Data;

import java.util.List;

@Data
public class PdtFeedingStationDTO extends PdtFeedingStation {
    List<PdtFeedingStationDetail> pdtFeedingStationDetails;
}
