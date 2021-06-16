package com.rh.i_mes_plus.service.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.sms.SmsWmsOutStockDetail;

import java.util.List;
import java.util.Map;

/**
 * 出库物料表
 *
 * @author hbq
 * @date 2020-11-02 14:32:34
 */
public interface ISmsWmsOutStockDetailService extends IService<SmsWmsOutStockDetail> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
    
    List<Map<String, Object>> getListByDocNo(Map<String, Object> map);
}

