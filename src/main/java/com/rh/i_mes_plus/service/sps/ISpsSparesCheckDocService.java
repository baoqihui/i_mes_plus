package com.rh.i_mes_plus.service.sps;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.SpsSparesCheckDocDTO;
import com.rh.i_mes_plus.model.sps.SpsSparesCheckDoc;

import java.util.Map;

/**
 * 盘点单信息表
 *
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
public interface ISpsSparesCheckDocService extends IService<SpsSparesCheckDoc> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Result getDocNo(Map<String, Object> map);

    Result close(SpsSparesCheckDocDTO spsSparesCheckDocDTO);
}

