package com.rh.i_mes_plus.service.sps;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.sps.GzkFixDetailInfo;

import java.util.List;
import java.util.Map;

/**
 * 工装治具
 *
 * @author hbq
 * @date 2021-02-23 10:06:16
 */
public interface IGzkFixDetailInfoService extends IService<GzkFixDetailInfo> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    List<Map> leadOut(List<Long> ids);
}

