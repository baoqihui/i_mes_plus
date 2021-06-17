package com.rh.i_mes_plus.service.iqc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.iqc.IqcOqaHistory;

import java.util.Map;

/**
 * OQA抽检历史记录
 *
 * @author hbq
 * @date 2020-10-22 16:28:19
 */
public interface IIqcOqaHistoryService extends IService<IqcOqaHistory> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

