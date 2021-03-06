package com.rh.i_mes_plus.service.sps;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sps.SpsMaintenLogInfo;

import java.util.List;
import java.util.Map;

/**
 * 保养记录信息表
 *
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
public interface ISpsMaintenLogInfoService extends IService<SpsMaintenLogInfo> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Result saveAll(SpsMaintenLogInfo spsMaintenLogInfo);

    Result allMaintain(List<SpsMaintenLogInfo> spsMaintenLogInfos);
}

