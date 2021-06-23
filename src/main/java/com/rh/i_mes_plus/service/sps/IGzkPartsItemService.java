package com.rh.i_mes_plus.service.sps;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.sps.GzkPartsItem;

import java.util.Map;

/**
 * 工装备品物料
 *
 * @author hbq
 * @date 2021-06-23 15:54:13
 */
public interface IGzkPartsItemService extends IService<GzkPartsItem> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

