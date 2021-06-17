package com.rh.i_mes_plus.service.iqc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.iqc.IqcOqaTestType;

import java.util.Map;

/**
 * 抽样方案
(正常、加严、放宽)
 *
 * @author hqb
 * @date 2020-10-16 10:06:17
 */
public interface IIqcOqaTestTypeService extends IService<IqcOqaTestType> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<IqcOqaTestType> findList(Map<String, Object> params);
}

