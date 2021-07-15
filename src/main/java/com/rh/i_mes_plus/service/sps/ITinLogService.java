package com.rh.i_mes_plus.service.sps;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.sps.TinLog;

import java.util.Map;

/**
 * 锡膏操作日志
 *
 * @author hbq
 * @date 2021-07-15 10:18:07
 */
public interface ITinLogService extends IService<TinLog> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

