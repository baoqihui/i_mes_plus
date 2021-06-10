package com.rh.i_mes_plus.service.gtoa;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.gtoa.FaiInfo;

import java.util.Map;

/**
 * fal
 *
 * @author hbq
 * @date 2020-11-05 20:14:46
 */
public interface IFaiInfoService extends IService<FaiInfo> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

