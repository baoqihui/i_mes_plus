package com.rh.i_mes_plus.service.sps;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.sps.SpsFeederInfo;

import java.util.Map;

/**
 * 料站表详情
 *
 * @author hbq
 * @date 2021-05-21 15:02:34
 */
public interface ISpsFeederInfoService extends IService<SpsFeederInfo> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

