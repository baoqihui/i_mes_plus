package com.rh.i_mes_plus.service.iqc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.iqc.IqcOqaItem;

import java.util.Map;

/**
 * 抽样项目
 *
 * @author hbq
 * @date 2020-10-22 16:28:19
 */
public interface IIqcOqaItemService extends IService<IqcOqaItem> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

