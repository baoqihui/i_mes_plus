package com.rh.i_mes_plus.dto;

import com.rh.i_mes_plus.model.pdt.PdtWmsOutStockDetail;
import com.rh.i_mes_plus.model.pdt.PdtWmsOutStockDoc;
import lombok.Data;

import java.util.List;

@Data
public class PdtWmsOutStockDocDTO extends PdtWmsOutStockDoc {
    private List<PdtWmsOutStockDetail> pdtWmsOutStockDetails;
}
