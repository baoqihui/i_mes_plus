package com.rh.i_mes_plus.service.sps;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.SpsEngFixReqInfoDTO;
import com.rh.i_mes_plus.model.sps.SpsEngFixReqInfo;

import java.util.Map;

/**
 * 工程治具借用记录表
 *
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
public interface ISpsEngFixReqInfoService extends IService<SpsEngFixReqInfo> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Result getDocNo(Map<String, Object> map);

    Result saveAll(SpsEngFixReqInfoDTO spsEngFixReqInfoDTO);

    Result close(SpsEngFixReqInfoDTO spsEngFixReqInfoDTO);
}

