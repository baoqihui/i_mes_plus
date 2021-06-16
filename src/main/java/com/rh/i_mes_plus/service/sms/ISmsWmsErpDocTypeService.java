package com.rh.i_mes_plus.service.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.sms.SmsWmsErpDocType;

import java.util.Map;

/**
 * ERP单据性质档
 *
 * @author hbq
 * @date 2021-01-19 09:52:44
 */
public interface ISmsWmsErpDocTypeService extends IService<SmsWmsErpDocType> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

