package com.rh.i_mes_plus.service.pdt;

import com.rh.i_mes_plus.model.pdt.PdtMoFeedingStationDetail;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 
 *
 * @author hbq
 * @date 2021-07-06 13:52:46
 */
public interface IPdtMoFeedingStationDetailService extends IService<PdtMoFeedingStationDetail> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

