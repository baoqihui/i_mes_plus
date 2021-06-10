package com.rh.i_mes_plus.dto;

import com.rh.i_mes_plus.model.gtoa.WmsWorkTrackingDetail;
import lombok.Data;

@Data
public class WmsWorkTrackingDetailDTO extends WmsWorkTrackingDetail {
    private String closeFlag;
}
