package com.rh.i_mes_plus.service.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.ums.UmsRoute;

import java.util.Map;

/**
 * 流程控制
 *
 * @author hbq
 * @date 2021-06-01 09:33:59
 */
public interface IUmsRouteService extends IService<UmsRoute> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

