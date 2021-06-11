package com.rh.i_mes_plus.dto;

import com.rh.i_mes_plus.model.other.WmsProjectBase;
import com.rh.i_mes_plus.model.other.WmsProjectDetail;
import lombok.Data;

import java.util.List;

@Data
public class WmsProjectDTO {
    private WmsProjectBase wmsProjectBase;
    private List<WmsProjectDetail> wmsProjectDetails;
}
