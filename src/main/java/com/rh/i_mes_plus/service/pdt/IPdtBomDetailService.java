package com.rh.i_mes_plus.service.pdt;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.pdt.PdtBomDetail;

import java.util.Map;

/**
 * 
 *
 * @author hbq
 * @date 2021-05-25 13:18:22
 */
public interface IPdtBomDetailService extends IService<PdtBomDetail> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

