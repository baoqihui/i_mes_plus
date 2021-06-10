package com.rh.i_mes_plus.service.other;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.other.SapMesLog;

import java.util.Map;

/**
 * sap-mes接口错误日志
 *
 * @author hbq
 * @date 2020-10-31 10:58:31
 */
public interface ISapMesLogService extends IService<SapMesLog> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

