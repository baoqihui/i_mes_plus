package com.rh.i_mes_plus.service.pdt;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.pdt.PdtWmsPmMoBase;

import java.util.Map;

/**
 * 指令单号表
 *
 * @author hbq
 * @date 2020-12-28 13:28:45
 */
public interface IPdtWmsPmMoBaseService extends IService<PdtWmsPmMoBase> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
    
    Map selDetailByMoNo(String moNo);

    Result saveNew(PdtWmsPmMoBase pdtWmsPmMoBase);

    String getNewNoByProjectId(String projectId);
}

