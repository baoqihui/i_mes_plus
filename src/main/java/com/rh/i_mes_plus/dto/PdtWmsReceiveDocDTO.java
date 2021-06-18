package com.rh.i_mes_plus.dto;

import com.rh.i_mes_plus.model.pdt.PdtWmsReceiveDetail;
import com.rh.i_mes_plus.model.pdt.PdtWmsReceiveDoc;
import lombok.Data;

import java.util.List;

@Data
public class PdtWmsReceiveDocDTO extends PdtWmsReceiveDoc {
    private List<PdtWmsReceiveDetail> pdtWmsReceiveDetails;
}
