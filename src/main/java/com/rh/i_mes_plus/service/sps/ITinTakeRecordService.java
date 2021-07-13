package com.rh.i_mes_plus.service.sps;

import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sps.TinTakeRecord;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.vo.TinTakeRecordVO;

import java.util.Map;

/**
 * 
 *
 * @author hbq
 * @date 2021-07-08 19:48:01
 */
public interface ITinTakeRecordService extends IService<TinTakeRecord> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<TinTakeRecordVO> findList(Map<String, Object> params);

    Result take(Map<String, Object> params);
}

