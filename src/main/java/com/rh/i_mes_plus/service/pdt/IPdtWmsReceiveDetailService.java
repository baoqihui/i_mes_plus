package com.rh.i_mes_plus.service.pdt;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.pdt.PdtWmsReceiveDetail;

import java.util.List;
import java.util.Map;

/**
 * 入库单明细表
 *
 * @author hbq
 * @date 2020-12-29 15:41:49
 */
public interface IPdtWmsReceiveDetailService extends IService<PdtWmsReceiveDetail> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    List<Map<String, Object>> listAll(String docNo);
}

