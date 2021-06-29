package com.rh.i_mes_plus.service.sms;

import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sms.SmsBarcodeRuleDetail;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 条码规则详情
 *
 * @author hbq
 * @date 2021-06-28 18:41:07
 */
public interface ISmsBarcodeRuleDetailService extends IService<SmsBarcodeRuleDetail> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Result presentToBefore(Map<String, Object> map);

    Result presentToAfter(Map<String, Object> map);
}

