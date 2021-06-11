package com.rh.i_mes_plus.service.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.ums.UmsItemMes;

import java.util.Map;

/**
 * 物料信息（mes获取）
 *
 * @author hqb
 * @date 2020-09-21 11:13:27
 */
public interface IUmsItemMesService extends IService<UmsItemMes> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<UmsItemMes> findList(Map<String, Object> params);

    Result saveAll(UmsItemMes umsItemMes);
}

