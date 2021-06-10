package com.rh.i_mes_plus.service.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.ums.UmsCoItemsource;

import java.util.Map;

/**
 * 物料类型
 *
 * @author hqb
 * @date 2020-09-21 16:35:09
 */
public interface IUmsCoItemsourceService extends IService<UmsCoItemsource> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<UmsCoItemsource> findList(Map<String, Object> params);
}

