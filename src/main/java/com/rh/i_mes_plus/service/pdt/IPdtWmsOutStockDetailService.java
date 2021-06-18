package com.rh.i_mes_plus.service.pdt;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.pdt.PdtWmsOutStockDetail;

import java.util.List;
import java.util.Map;

/**
 * 成品出库详情表
 *
 * @author hbq
 * @date 2021-01-06 15:22:23
 */
public interface IPdtWmsOutStockDetailService extends IService<PdtWmsOutStockDetail> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    List<Map<String, Object>> getListByDocNo(Map<String, Object> map);
}

