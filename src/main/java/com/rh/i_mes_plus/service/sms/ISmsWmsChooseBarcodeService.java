package com.rh.i_mes_plus.service.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sms.SmsWmsChooseBarcode;

import java.util.Map;

/**
 * 
 *
 * @author hbq
 * @date 2021-02-25 16:12:27
 */
public interface ISmsWmsChooseBarcodeService extends IService<SmsWmsChooseBarcode> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Result choose(Map<String, Object> params);

    Result close(Map<String, Object> params);
}

