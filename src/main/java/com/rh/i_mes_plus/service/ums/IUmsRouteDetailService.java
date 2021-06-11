package com.rh.i_mes_plus.service.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.ums.UmsRouteDetail;

import java.util.Map;

/**
 * 流程控制详情
 *
 * @author hbq
 * @date 2021-06-01 09:33:59
 */
public interface IUmsRouteDetailService extends IService<UmsRouteDetail> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

