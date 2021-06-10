package com.rh.i_mes_plus.dto;

import com.rh.i_mes_plus.model.gtoa.TaskExecuteInfo;
import com.rh.i_mes_plus.model.gtoa.VerControlDelivery;
import lombok.Data;

import java.util.List;

@Data
public class TaskExecuteInfoDTO {
    private TaskExecuteInfo taskExecuteInfo;
    private List<VerControlDelivery> verControlDeliveryS;
}
