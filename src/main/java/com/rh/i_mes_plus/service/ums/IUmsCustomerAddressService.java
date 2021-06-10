package com.rh.i_mes_plus.service.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.ums.UmsCustomerAddress;

import java.util.Map;

/**
 * 客户地址
 *
 * @author hqb
 * @date 2020-09-21 08:41:57
 */
public interface IUmsCustomerAddressService extends IService<UmsCustomerAddress> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<UmsCustomerAddress> findList(Map<String, Object> params);
}

