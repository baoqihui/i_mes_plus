package com.rh.i_mes_plus.service.iqc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.iqc.IqcCoQcPlan;

import java.util.Map;

/**
 * 抽样方案明细
 *
 * @author hqb
 * @date 2020-10-16 10:06:17
 */
public interface IIqcCoQcPlanService extends IService<IqcCoQcPlan> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<IqcCoQcPlan> findList(Map<String, Object> params);
}

