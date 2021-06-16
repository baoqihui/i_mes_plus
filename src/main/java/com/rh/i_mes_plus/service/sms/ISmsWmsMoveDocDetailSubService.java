package com.rh.i_mes_plus.service.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.sms.SmsWmsMoveDocDetailSub;

import java.util.Map;

/**
 * 调拨单日志操作明细表
 *
 * @author hbq
 * @date 2020-12-11 13:39:17
 */
public interface ISmsWmsMoveDocDetailSubService extends IService<SmsWmsMoveDocDetailSub> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
    
    Page<Map> listAll(Map<String, Object> params);
    
    Page<Map> listAllCollect(Map<String, Object> params);
}

