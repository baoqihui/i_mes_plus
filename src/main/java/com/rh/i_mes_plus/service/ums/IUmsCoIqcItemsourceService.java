package com.rh.i_mes_plus.service.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.ums.UmsCoIqcItemsource;

import java.util.Map;

/**
 * IQC检验分类表
 *
 * @author hqb
 * @date 2020-09-21 16:35:09
 */
public interface IUmsCoIqcItemsourceService extends IService<UmsCoIqcItemsource> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<UmsCoIqcItemsource> findList(Map<String, Object> params);
}

