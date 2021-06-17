package com.rh.i_mes_plus.service.sps;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.sps.SpsEngFixDetailInfo;

import java.util.List;
import java.util.Map;

/**
 * 工程治具详情表
 *
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
public interface ISpsEngFixDetailInfoService extends IService<SpsEngFixDetailInfo> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    List<Map<String, Object>> selItemTypeInfosByModelCode(String modelCode);

    String getPosesByFixNos(List<String> fixNos);
}

