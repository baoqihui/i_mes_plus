package com.rh.i_mes_plus.service.sms;

import com.rh.i_mes_plus.model.sms.SmsLightColor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 仓库灯资源占用
 *
 * @author hbq
 * @date 2021-06-24 15:19:55
 */
public interface ISmsLightColorService extends IService<SmsLightColor> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

