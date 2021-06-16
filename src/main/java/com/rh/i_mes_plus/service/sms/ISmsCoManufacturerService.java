package com.rh.i_mes_plus.service.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sms.SmsCoManufacturer;

import java.util.List;
import java.util.Map;

/**
 * BOM料件制造商表
 *
 * @author hbq
 * @date 2020-10-31 15:56:15
 */
public interface ISmsCoManufacturerService extends IService<SmsCoManufacturer> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Result saveAll(List<Map<String, Object>> maps);
}

