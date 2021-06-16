package com.rh.i_mes_plus.service.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.sms.SmsWmsMoveDocDetail;

import java.util.Map;

/**
 * 调拨单物料明细表
 *
 * @author hbq
 * @date 2020-12-11 13:39:17
 */
public interface ISmsWmsMoveDocDetailService extends IService<SmsWmsMoveDocDetail> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

