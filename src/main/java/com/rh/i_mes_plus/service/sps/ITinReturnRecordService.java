package com.rh.i_mes_plus.service.sps;

import com.rh.i_mes_plus.model.sps.TinReturnRecord;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 红锡膏退仓记录
 *
 * @author hbq
 * @date 2021-07-08 19:48:01
 */
public interface ITinReturnRecordService extends IService<TinReturnRecord> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

