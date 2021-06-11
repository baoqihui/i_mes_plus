package com.rh.i_mes_plus.vo;

import lombok.Data;

import java.util.List;

@Data
public class OneVO {
    private Long id;
    private String name;
    private String code;
    private Boolean status;
    private List<TwoVO> twoVOS;
}
