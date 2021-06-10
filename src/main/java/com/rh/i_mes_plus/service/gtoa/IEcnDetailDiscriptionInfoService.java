package com.rh.i_mes_plus.service.gtoa;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.gtoa.EcnDetailDiscriptionInfo;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author hbq
 * @date 2020-10-22 19:36:56
 */
public interface IEcnDetailDiscriptionInfoService extends IService<EcnDetailDiscriptionInfo> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Result QAcheck(Map<String, List<EcnDetailDiscriptionInfo>> map);

    Result QAcheckSuccess(Map<String, List<EcnDetailDiscriptionInfo>> map);
}

