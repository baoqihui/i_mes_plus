package com.rh.i_mes_plus.dto;

import com.rh.i_mes_plus.model.pdt.PdtBom;
import com.rh.i_mes_plus.model.pdt.PdtBomDetail;
import lombok.Data;

import java.util.List;

@Data
public class PdtBomDTO extends PdtBom {
    private List<PdtBomDetail> pdtBomDetails;
}
