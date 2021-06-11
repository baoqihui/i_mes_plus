package com.rh.i_mes_plus.dto;

import lombok.Data;

@Data
public class UmsWmsAreaDTO {
    private Integer oneStart;
    private Integer oneEnd;
    private Integer twoStart;
    private Integer twoEnd;
    private String arFatherSn;
    private String connector;
    private String arState;
    private Long arArea;
    private Long arAreaUnit;
    private Long arCubage;
    private Long arCubageUnit;
    private String arIsAging;
    private String arRemark;
}
