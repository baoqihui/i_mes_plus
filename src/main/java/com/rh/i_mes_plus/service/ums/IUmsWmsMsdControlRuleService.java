package com.rh.i_mes_plus.service.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.ums.UmsWmsMsdControlRule;

import java.util.Map;

/**
 * MSD管控规则
 *
 * @author hqb
 * @date 2020-09-21 16:35:09
 */
public interface IUmsWmsMsdControlRuleService extends IService<UmsWmsMsdControlRule> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<UmsWmsMsdControlRule> findList(Map<String, Object> params);
}

