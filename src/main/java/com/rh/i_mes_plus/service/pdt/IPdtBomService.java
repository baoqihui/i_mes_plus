package com.rh.i_mes_plus.service.pdt;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.PdtBomDTO;
import com.rh.i_mes_plus.model.pdt.PdtBom;

import java.util.Map;

/**
 * bom表
 *
 * @author hbq
 * @date 2021-05-25 13:18:22
 */
public interface IPdtBomService extends IService<PdtBom> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Result saveAll(PdtBomDTO pdtBomDTO);

    Result getListByItemCode(Map<String, Object> params);

    Result getPcbMapByItemCode(Map<String, Object> params);

    Result changeStage(PdtBom pdtBom);
}

