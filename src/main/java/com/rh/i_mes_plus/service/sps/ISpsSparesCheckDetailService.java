package com.rh.i_mes_plus.service.sps;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.sps.SpsSparesCheckDetail;

import java.util.List;
import java.util.Map;

/**
 * 盘点明细表
 *
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
public interface ISpsSparesCheckDetailService extends IService<SpsSparesCheckDetail> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    List<Map<String, Object>> getDetailByCheckNo(String checkNo);
}

