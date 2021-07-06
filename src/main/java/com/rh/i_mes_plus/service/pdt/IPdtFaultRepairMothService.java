package com.rh.i_mes_plus.service.pdt;

import com.rh.i_mes_plus.model.pdt.PdtFaultRepairMoth;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 维修方法
 *
 * @author hbq
 * @date 2021-07-05 10:11:08
 */
public interface IPdtFaultRepairMothService extends IService<PdtFaultRepairMoth> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

