package com.rh.i_mes_plus.service.sps;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sps.GzkPartsReqInfo;

import java.util.Map;

/**
 * 工装备品借用记录表
 *
 * @author hbq
 * @date 2021-02-21 10:39:50
 */
public interface IGzkPartsReqInfoService extends IService<GzkPartsReqInfo> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Result getDocNo(Map<String, Object> map);

    Result close(GzkPartsReqInfo gzkPartsReqInfo);

}

