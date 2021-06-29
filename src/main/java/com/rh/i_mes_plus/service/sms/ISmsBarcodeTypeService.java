package com.rh.i_mes_plus.service.sms;

import com.rh.i_mes_plus.model.sms.SmsBarcodeType;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 条码类型
 *
 * @author hbq
 * @date 2021-06-28 18:41:07
 */
public interface ISmsBarcodeTypeService extends IService<SmsBarcodeType> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

