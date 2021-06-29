package com.rh.i_mes_plus.service.sms;

import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.SmsBarcodeRuleDTO;
import com.rh.i_mes_plus.model.sms.SmsBarcodeRule;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 条码规则管理
 *
 * @author hbq
 * @date 2021-06-28 18:41:06
 */
public interface ISmsBarcodeRuleService extends IService<SmsBarcodeRule> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Result saveAll(SmsBarcodeRuleDTO smsBarcodeRuleDTO);
}

