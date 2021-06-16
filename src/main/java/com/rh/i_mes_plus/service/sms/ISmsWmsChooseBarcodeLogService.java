package com.rh.i_mes_plus.service.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.sms.SmsWmsChooseBarcodeLog;

import java.util.Map;

/**
 * 挑料日志表
 *
 * @author hbq
 * @date 2021-03-01 14:31:55
 */
public interface ISmsWmsChooseBarcodeLogService extends IService<SmsWmsChooseBarcodeLog> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

