package com.rh.i_mes_plus.service.sps;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.GzkFixReqInfoDTO;
import com.rh.i_mes_plus.model.sps.GzkFixReqInfo;

import java.util.Map;

/**
 * 工装治具借用记录表
 *
 * @author hbq
 * @date 2021-03-09 10:59:40
 */
public interface IGzkFixReqInfoService extends IService<GzkFixReqInfo> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Result getDocNo(Map<String, Object> map);

    Result close(GzkFixReqInfoDTO gzkFixReqInfoDTO);
}

