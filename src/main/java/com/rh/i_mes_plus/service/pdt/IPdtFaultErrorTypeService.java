package com.rh.i_mes_plus.service.pdt;

import com.rh.i_mes_plus.model.pdt.PdtFaultErrorType;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 不良类型
 *
 * @author hbq
 * @date 2021-07-05 10:11:08
 */
public interface IPdtFaultErrorTypeService extends IService<PdtFaultErrorType> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

