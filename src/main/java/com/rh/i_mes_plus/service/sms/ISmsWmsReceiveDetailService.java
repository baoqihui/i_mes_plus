package com.rh.i_mes_plus.service.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.sms.SmsWmsReceiveDetail;

import java.util.Map;

/**
 * 
 *
 * @author hqb
 * @date 2020-10-07 15:25:52
 */
public interface ISmsWmsReceiveDetailService extends IService<SmsWmsReceiveDetail> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<SmsWmsReceiveDetail> findList(Map<String, Object> params);
}

