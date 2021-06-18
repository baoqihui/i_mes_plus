package com.rh.i_mes_plus.service.pdt;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.PdtFeedingStationDTO;
import com.rh.i_mes_plus.model.pdt.PdtFeedingStation;

import java.util.Map;

/**
 * 料站表主表
 *
 * @author hbq
 * @date 2021-05-24 10:17:57
 */
public interface IPdtFeedingStationService extends IService<PdtFeedingStation> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Result saveAll(PdtFeedingStationDTO pdtFeedingStationDTO);
}

