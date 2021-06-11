package com.rh.i_mes_plus.service.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.ums.UmsSupplier;

import java.util.List;
import java.util.Map;

/**
 * BOM料件供应商表
 *
 * @author hqb
 * @date 2020-09-19 14:42:47
 */
public interface IUmsSupplierService extends IService<UmsSupplier> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<UmsSupplier> findList(Map<String, Object> params);

    Result saveAll(List<Map<String, Object>> maps);
}

