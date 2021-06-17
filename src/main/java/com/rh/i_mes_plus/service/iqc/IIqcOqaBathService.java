package com.rh.i_mes_plus.service.iqc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.iqc.IqcOqaBath;

import java.util.Map;

/**
 * 检验单明细
 *
 * @author hbq
 * @date 2020-10-21 14:29:19
 */
public interface IIqcOqaBathService extends IService<IqcOqaBath> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

