package com.rh.i_mes_plus.service.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.sms.SmsWmsPo;

import java.util.Map;

/**
 * 采购订单
 *
 * @author hqb
 * @date 2020-09-29 15:58:30
 */
public interface ISmsWmsPoService extends IService<SmsWmsPo> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<SmsWmsPo> findList(Map<String, Object> params);
}

