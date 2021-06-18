package com.rh.i_mes_plus.service.pdt;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.pdt.PdtWmsOutStockList;

import java.util.Map;

/**
 * 成品出库明细表
 *
 * @author hbq
 * @date 2021-01-06 15:22:23
 */
public interface IPdtWmsOutStockListService extends IService<PdtWmsOutStockList> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Result ecc(String docNo);

    Page<Map> listAll(Map<String, Object> params);

    Page<Map> listAllCollect(Map<String, Object> params);

    Page<Map> listAllCollectByBoxNo(Map<String, Object> params);
}

