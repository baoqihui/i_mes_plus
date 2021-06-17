package com.rh.i_mes_plus.service.iqc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.iqc.IqcOqaSingle;

import java.util.Map;

/**
 * 抽验样本信息
 *
 * @author hbq
 * @date 2020-10-23 11:39:35
 */
public interface IIqcOqaSingleService extends IService<IqcOqaSingle> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

