package com.rh.i_mes_plus.service.gtoa;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.gtoa.WmsWorkTracingMain;

import java.util.Map;

/**
 * 
 *
 * @author hbq
 * @date 2020-12-02 15:59:56
 */
public interface IWmsWorkTracingMainService extends IService<WmsWorkTracingMain> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

