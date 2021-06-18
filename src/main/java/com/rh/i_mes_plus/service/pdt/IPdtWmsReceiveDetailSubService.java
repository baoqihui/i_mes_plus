package com.rh.i_mes_plus.service.pdt;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.pdt.PdtWmsReceiveDetailSub;

import java.util.Map;

/**
 * 入库单明细表
 *
 * @author hbq
 * @date 2020-12-29 15:41:49
 */
public interface IPdtWmsReceiveDetailSubService extends IService<PdtWmsReceiveDetailSub> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Page<Map> listAll(Map<String, Object> params);

    Page<Map> listAllCollect(Map<String, Object> params);

    Page<Map> listAllCollectByBoxNo(Map<String, Object> params);
}

