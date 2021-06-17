package com.rh.i_mes_plus.service.sps;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.sps.SpsEngFixReqDetailInfo;

import java.util.List;
import java.util.Map;

/**
 * 工程治具借出详情表
 *
 * @author hbq
 * @date 2021-02-20 09:14:10
 */
public interface ISpsEngFixReqDetailInfoService extends IService<SpsEngFixReqDetailInfo> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    List<Map<String, Object>> getReqDetail(String reqNo);
}

