package com.rh.i_mes_plus.service.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.ums.UmsFeederSpec;

import java.util.Map;

/**
 * 料枪规格
 *
 * @author hbq
 * @date 2021-05-21 15:02:34
 */
public interface IUmsFeederSpecService extends IService<UmsFeederSpec> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

