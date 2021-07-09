package com.rh.i_mes_plus.service.sps;

import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sps.TinReceiveRecord;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 锡膏红胶入库表
 *
 * @author hbq
 * @date 2021-07-08 19:48:01
 */
public interface ITinReceiveRecordService extends IService<TinReceiveRecord> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Result getDocNo(Map<String, Object> map);
}

