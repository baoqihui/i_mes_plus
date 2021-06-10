package com.rh.i_mes_plus.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CheckWarehouseEnum {
    S_1(1,"免检", "良品库"),
    S_2(2,"待检","待检库"),
    S_3(3, "检验合格","良品库"),
    S_4(4,"检验不合格","不良品库"),
    S_5(5,"备料","生产库"),
    S_6(6,"","报废库"),
    S_7(7,"取消备料","良品库"),
    S_8(8,"拆签",""),
    S_9(9,"合签",""),

    ;
    private int checkState;

    private String checkName;

    private String warehouse;


}
