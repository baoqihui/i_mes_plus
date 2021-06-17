package com.rh.i_mes_plus.service.sps;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.sps.MfgStencilsReqInfo;

import java.util.Map;

/**
 * 钢网借出详情表
 *
 * @author hbq
 * @date 2021-06-03 20:11:19
 */
public interface IMfgStencilsReqInfoService extends IService<MfgStencilsReqInfo> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

