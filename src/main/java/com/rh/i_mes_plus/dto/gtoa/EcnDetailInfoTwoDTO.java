package com.rh.i_mes_plus.dto.gtoa;

import com.rh.i_mes_plus.model.gtoa.EcnDetailInfo;
import lombok.Data;

@Data
public class EcnDetailInfoTwoDTO extends EcnDetailInfo {
    /**回退步数*/
    private Integer rollBackStep;
}
