package com.rh.i_mes_plus.service.pdt;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.pdt.PdtWmsBoxBarcode;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author hbq
 * @date 2020-12-28 13:28:45
 */
public interface IPdtWmsBoxBarcodeService extends IService<PdtWmsBoxBarcode> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Result unboxing(Map<String,Object> map);

    Result boxing(Map<String, Object> map);

    List<Map<String, Object>> listByBoxNo(String boxNo);
}

