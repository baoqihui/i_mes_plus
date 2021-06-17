package com.rh.i_mes_plus.service.iqc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.iqc.IqcOqaCheckBasis;

import java.util.Map;

/**
 * OQA检验依据
 *
 * @author hqb
 * @date 2020-10-16 10:06:17
 */
public interface IIqcOqaCheckBasisService extends IService<IqcOqaCheckBasis> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<IqcOqaCheckBasis> findList(Map<String, Object> params);
}

