package com.rh.i_mes_plus.service.pdt;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.PdtWmsOutStockDocDTO;
import com.rh.i_mes_plus.model.pdt.PdtWmsOutStockDoc;

import java.util.List;
import java.util.Map;

/**
 * 成品出库单主表
 *
 * @author hbq
 * @date 2021-01-06 15:22:23
 */
public interface IPdtWmsOutStockDocService extends IService<PdtWmsOutStockDoc> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Result getDocNo(Map<String, Object> map);

    Result saveAll(PdtWmsOutStockDocDTO pdtWmsOutStockDocDTO);

    Result removeAll(Map<String, List<Long>> map);

    Result pdaPdtOutStock(Map<String, Object> map);

    List<String> getOutStockDocListByDtCode(Map<String, Object> map);

    Result statement(Map<String, Object> map);

    Result close(Map<String, Object> map);
}

