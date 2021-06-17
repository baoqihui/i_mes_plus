package com.rh.i_mes_plus.service.sps;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.sps.SpsMaintenCycleInfo;

import java.util.Map;

/**
 * 保养周期信息表
 *
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
public interface ISpsMaintenCycleInfoService extends IService<SpsMaintenCycleInfo> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

