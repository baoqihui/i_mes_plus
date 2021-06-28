package com.rh.i_mes_plus.dto;

import com.rh.i_mes_plus.model.sps.SpsSparesCheckDetail;
import com.rh.i_mes_plus.model.sps.SpsSparesCheckDoc;
import lombok.Data;

import java.util.List;

@Data
public class SpsSparesCheckDocDTO extends SpsSparesCheckDoc {
    private List<SpsSparesCheckDetail> sparesCheckDetails;
}
