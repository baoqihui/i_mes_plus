package com.rh.i_mes_plus.service.iqc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.iqc.IqcOqaTestStep;

import java.util.Map;

/**
 * 检验阶
 *
 * @author hqb
 * @date 2020-10-16 10:06:17
 */
public interface IIqcOqaTestStepService extends IService<IqcOqaTestStep> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<IqcOqaTestStep> findList(Map<String, Object> params);
}

