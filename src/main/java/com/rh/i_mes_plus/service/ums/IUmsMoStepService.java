package com.rh.i_mes_plus.service.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.ums.UmsMoStep;

import java.util.Map;

/**
 * 工单阶别(0-SMT,1-WAVE,2-Hand Soldering,3-Assembly,4-Others)
 *
 * @author hbq
 * @date 2021-05-31 10:07:36
 */
public interface IUmsMoStepService extends IService<UmsMoStep> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

