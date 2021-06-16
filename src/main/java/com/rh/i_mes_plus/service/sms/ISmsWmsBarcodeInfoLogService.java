package com.rh.i_mes_plus.service.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.sms.SmsWmsBarcodeInfoLog;

import java.util.Map;

/**
 * 条码信息(临时表)
 *
 * @author hqb
 * @date 2020-10-07 14:27:41
 */
public interface ISmsWmsBarcodeInfoLogService extends IService<SmsWmsBarcodeInfoLog> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<SmsWmsBarcodeInfoLog> findList(Map<String, Object> params);

}

