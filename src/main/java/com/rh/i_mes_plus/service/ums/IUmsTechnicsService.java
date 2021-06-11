package com.rh.i_mes_plus.service.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.ums.UmsTechnics;

import java.util.Map;

/**
 * 工艺
 *
 * @author hbq
 * @date 2021-06-01 10:57:04
 */
public interface IUmsTechnicsService extends IService<UmsTechnics> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Map<String, Object> isPcbUnpack(Long id);
}

