package com.rh.i_mes_plus.service.gtoa;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.gtoa.TimeLog;

import java.util.Map;

/**
 * 时间日志
 *
 * @author hbq
 * @date 2020-11-03 19:54:46
 */
public interface ITimeLogService extends IService<TimeLog> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

