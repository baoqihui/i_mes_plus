package com.rh.i_mes_plus.service.gtoa;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.gtoa.VerControlDelivery;

import java.util.Map;

/**
 * 不允许发货版本控制
 *
 * @author hbq
 * @date 2020-11-02 18:30:05
 */
public interface IVerControlDeliveryService extends IService<VerControlDelivery> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
    public Result ecnVerControlInfo(Map<String, Object> params);
}

