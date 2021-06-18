package com.rh.i_mes_plus.dto;

import com.rh.i_mes_plus.model.pdt.PdtWmsBox;
import com.rh.i_mes_plus.model.pdt.PdtWmsBoxBarcode;
import lombok.Data;

import java.util.List;

@Data
public class PdtWmsBoxDTO extends PdtWmsBox {
    private List<PdtWmsBoxBarcode> pdtWmsBoxBarcode;
}
