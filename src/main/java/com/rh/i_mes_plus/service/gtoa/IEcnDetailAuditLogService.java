package com.rh.i_mes_plus.service.gtoa;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.gtoa.EcnDetailAuditLog;

import java.util.Map;

/**
 * ECN详情表
 *
 * @author hbq
 * @date 2020-10-26 18:58:29
 */
public interface IEcnDetailAuditLogService extends IService<EcnDetailAuditLog> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

