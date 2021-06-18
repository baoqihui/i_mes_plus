package com.rh.i_mes_plus.service.pdt;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.pdt.PdtFeedingStationDetail;

import java.util.Map;

/**
 * 料站表详情
 *
 * @author hbq
 * @date 2021-05-24 10:17:57
 */
public interface IPdtFeedingStationDetailService extends IService<PdtFeedingStationDetail> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

