package com.rh.i_mes_plus.vo;

import com.rh.i_mes_plus.model.ums.UmsDepa;
import lombok.Data;

import java.util.List;

@Data
public class DepaTreeVO extends UmsDepa {
    //存放子级集合
    private List<DepaTreeVO> children;
}
