package com.rh.i_mes_plus.service.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.SmsWmsOutStockDocDTO;
import com.rh.i_mes_plus.model.sms.SmsWmsOutStockDoc;

import java.util.List;
import java.util.Map;

/**
 * 出库单主表
 *
 * @author hbq
 * @date 2020-11-02 14:32:34
 */
public interface ISmsWmsOutStockDocService extends IService<SmsWmsOutStockDoc> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Result saveAll(SmsWmsOutStockDocDTO smsWmsOutStockDocDTO);

    Result saveProjectAll(SmsWmsOutStockDocDTO smsWmsOutStockDocDTO);
    
    Result pdaOutStock(Map<String, Object> map);
    
    Result getDocNo(Map<String, Object> map);
    
    Result getDocNoByProjectIdAndDocNo(Map<String, Object> map);
    
    Result getItemNumByDocNos(Map<String, List<String>> map);
    
    Result removeAll(Map<String, List<Long>> map);
    
    Result saveListAll(SmsWmsOutStockDocDTO smsWmsOutStockDocDTO);
    
    Result statement(Map<String, Object> map);

    Result pdaCancelOutStock(Map<String, Object> map);

    Result lightUp(Map<String, Object> params);

    Result cancelLightUp(Map<String, Object> params);
}

