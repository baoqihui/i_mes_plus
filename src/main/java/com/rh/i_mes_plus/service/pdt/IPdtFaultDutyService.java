package com.rh.i_mes_plus.service.pdt;

import com.rh.i_mes_plus.model.pdt.PdtFaultDuty;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 不良责任
 *
 * @author hbq
 * @date 2021-07-05 10:11:08
 */
public interface IPdtFaultDutyService extends IService<PdtFaultDuty> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

