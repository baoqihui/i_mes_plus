package com.rh.i_mes_plus.service.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.sms.SmsWmsIoType;

import java.util.Map;

/**
 * 单据类型表
 *
 * @author hbq
 * @date 2021-01-19 09:52:44
 */
public interface ISmsWmsIoTypeService extends IService<SmsWmsIoType> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

