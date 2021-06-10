package com.rh.i_mes_plus.vo;

import lombok.Data;

import java.util.List;

@Data
public class ChildVO {
    private Long id;
    private String name;
    private String code;
    private String parentCode;
    private List<ChildVO> childVOS;
}
