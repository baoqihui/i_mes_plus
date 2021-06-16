package com.rh.i_mes_plus.service.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.SmsWmsReloadDocDTO;
import com.rh.i_mes_plus.model.sms.SmsWmsReloadDoc;

import java.util.List;
import java.util.Map;

/**
 * 调拨单
 *
 * @author hbq
 * @date 2020-12-16 14:55:14
 */
public interface ISmsWmsReloadDocService extends IService<SmsWmsReloadDoc> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
    
    Page<Map> getStockInfoBySn(Map<String, Object> params);
    
    Result getDocNo(Map<String, Object> map);
    
    Result saveAll(SmsWmsReloadDocDTO smsWmsReloadDocDTO);
    
    Result removeAll(Map<String, List<Long>> map);
    
    Result pdaReload(Map<String, Object> map);
}

