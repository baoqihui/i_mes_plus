package com.rh.i_mes_plus.service.sps;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.sps.MfgStencilsDetailInfo;

import java.util.Map;

/**
 * 钢网详情信息表
 *
 * @author hbq
 * @date 2021-02-23 16:28:09
 */
public interface IMfgStencilsDetailInfoService extends IService<MfgStencilsDetailInfo> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

