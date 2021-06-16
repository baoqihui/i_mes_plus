package com.rh.i_mes_plus.service.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.sms.SmsWmsReloadDocDetail;

import java.util.List;
import java.util.Map;

/**
 * 调拨单物料明细表
 *
 * @author hbq
 * @date 2020-12-16 14:55:14
 */
public interface ISmsWmsReloadDocDetailService extends IService<SmsWmsReloadDocDetail> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
    
    List<Map<String,Object>> listAll(String reloadNo);
}

