package com.rh.i_mes_plus.service.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.ums.UmsItemMes;
import com.rh.i_mes_plus.model.ums.UmsItemSap;

import java.util.List;
import java.util.Map;

/**
 * 物料信息（sap获取）
 *
 * @author hqb
 * @date 2020-09-21 11:13:27
 */
public interface IUmsItemSapService extends IService<UmsItemSap> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Result saveAll(List<UmsItemSap> umsItemSaps);

    Page<UmsItemMes> findList2(Map<String, Object> params);
}

