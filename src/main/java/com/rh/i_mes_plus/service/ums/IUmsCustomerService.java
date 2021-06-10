package com.rh.i_mes_plus.service.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.ums.UmsCustomer;

import java.util.List;
import java.util.Map;

/**
 * 客户管理表
 *
 * @author hqb
 * @date 2020-09-21 08:41:57
 */
public interface IUmsCustomerService extends IService<UmsCustomer> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<UmsCustomer> findList(Map<String, Object> params);

    Result saveAll(List<Map<String, Object>> maps);
}

