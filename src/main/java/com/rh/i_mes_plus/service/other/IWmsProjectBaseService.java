package com.rh.i_mes_plus.service.other;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.other.WmsProjectBase;

import java.util.Map;

/**
 * 工单信息主表
 *
 * @author hbq
 * @date 2021-05-27 08:41:55
 */
public interface IWmsProjectBaseService extends IService<WmsProjectBase> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

