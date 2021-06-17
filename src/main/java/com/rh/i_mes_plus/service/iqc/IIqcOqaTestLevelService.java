package com.rh.i_mes_plus.service.iqc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.iqc.IqcOqaTestLevel;

import java.util.Map;

/**
 * 机种抽样方案
 *
 * @author hqb
 * @date 2020-10-16 11:25:37
 */
public interface IIqcOqaTestLevelService extends IService<IqcOqaTestLevel> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Integer getModelAndLevel(String itemCode);
}

