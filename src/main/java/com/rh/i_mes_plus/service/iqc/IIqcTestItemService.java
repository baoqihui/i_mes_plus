package com.rh.i_mes_plus.service.iqc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.iqc.IqcTestItem;

import java.util.Map;

/**
 * 检测项目
 *
 * @author hqb
 * @date 2020-10-16 10:06:17
 */
public interface IIqcTestItemService extends IService<IqcTestItem> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

