package com.rh.i_mes_plus.service.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.sms.SmsWmsOutStockList;

import java.util.List;
import java.util.Map;

/**
 * 出库明细表
 *
 * @author hbq
 * @date 2020-11-02 14:32:34
 */
public interface ISmsWmsOutStockListService extends IService<SmsWmsOutStockList> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
    
    Page<Map> listAll(Map<String, Object> params);
    
    Page<Map> listAllCollect(Map<String, Object> params);

    List<Map<String, Object>> listAndBarcode(String osdId);
}

