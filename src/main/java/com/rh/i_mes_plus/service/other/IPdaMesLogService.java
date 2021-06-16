package com.rh.i_mes_plus.service.other;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.other.PdaMesLog;

import java.util.Map;

/**
 * pda与mes操作日志
 *
 * @author hbq
 * @date 2021-01-28 17:56:25
 */
public interface IPdaMesLogService extends IService<PdaMesLog> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

