package com.rh.i_mes_plus.service.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.ums.UmsFeederType;

import java.util.Map;

/**
 * 料枪类型
 *
 * @author hbq
 * @date 2021-05-21 15:02:34
 */
public interface IUmsFeederTypeService extends IService<UmsFeederType> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

