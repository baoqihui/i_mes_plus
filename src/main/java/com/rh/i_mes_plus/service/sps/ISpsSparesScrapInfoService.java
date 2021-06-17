package com.rh.i_mes_plus.service.sps;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.sps.SpsSparesScrapInfo;

import java.util.Map;

/**
 * 报废信息表
 *
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
public interface ISpsSparesScrapInfoService extends IService<SpsSparesScrapInfo> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

