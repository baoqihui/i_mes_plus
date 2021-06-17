package com.rh.i_mes_plus.service.sps;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.sps.EngEquDetailInfo;

import java.util.Map;

/**
 * 工程设备详情信息表
 *
 * @author hbq
 * @date 2021-02-24 18:51:21
 */
public interface IEngEquDetailInfoService extends IService<EngEquDetailInfo> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

