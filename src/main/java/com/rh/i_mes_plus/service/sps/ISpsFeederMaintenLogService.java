package com.rh.i_mes_plus.service.sps;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.sps.SpsFeederMaintenLog;

import java.util.Map;

/**
 * 设备保养维修管理
 *
 * @author hbq
 * @date 2021-06-08 17:55:16
 */
public interface ISpsFeederMaintenLogService extends IService<SpsFeederMaintenLog> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

