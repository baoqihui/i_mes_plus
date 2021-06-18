package com.rh.i_mes_plus.service.pdt;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.PdtWmsReceiveDocDTO;
import com.rh.i_mes_plus.model.pdt.PdtWmsReceiveDoc;

import java.util.List;
import java.util.Map;

/**
 * 入库单表
 *
 * @author hbq
 * @date 2020-12-29 15:41:49
 */
public interface IPdtWmsReceiveDocService extends IService<PdtWmsReceiveDoc> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Result getDocNo(Map<String, Object> map);

    Result saveAll(PdtWmsReceiveDocDTO pdtWmsReceiveDocDTO);

    Result removeAll(Map<String, List<Long>> map);

    Result pdaPdtReceive(Map<String, Object> map);

    List<String>  getReceiveDocListByDtCode(Map<String, Object> map);

    Result close(Map<String, Object> map);
}

