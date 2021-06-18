package com.rh.i_mes_plus.service.pdt;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.pdt.PdtWmsStockInfo;
import com.rh.i_mes_plus.model.ums.UmsItemMes;

import java.util.Map;

/**
 * 库存信息表(修改此表：如果在库数量为0时，需要把相关信息保存至表T_WMS_STOCK_ZERO_INFO，退料时才能得到SN原有的相关信息)
 *
 * @author hbq
 * @date 2020-12-28 14:29:31
 */
public interface IPdtWmsStockInfoService extends IService<PdtWmsStockInfo> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Page<UmsItemMes> findList2(Map<String, Object> params);

    Page<Map> listAll(Map<String, Object> params);

    Page<Map> listAllCollect(Map<String, Object> params);

    Page<Map> listAllCollectByBoxNo(Map<String, Object> params);
}

