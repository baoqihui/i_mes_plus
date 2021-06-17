package com.rh.i_mes_plus.service.sps;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.sps.EngEquMaintenLog;

import java.util.Map;

/**
 * 设备保养维修管理
 *
 * @author hbq
 * @date 2021-06-08 15:32:04
 */
public interface IEngEquMaintenLogService extends IService<EngEquMaintenLog> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

