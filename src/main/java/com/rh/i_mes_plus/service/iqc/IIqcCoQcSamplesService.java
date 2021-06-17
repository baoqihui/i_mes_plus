package com.rh.i_mes_plus.service.iqc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.iqc.IqcCoQcSamples;

import java.util.List;
import java.util.Map;

/**
 * 样本代码
 *
 * @author hbq
 * @date 2020-10-23 15:24:30
 */
public interface IIqcCoQcSamplesService extends IService<IqcCoQcSamples> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    String getCodeBySendAmount(Long oqcSendAmount,Long otgId);

    List<Map<String, Object>> selList(Map<String, Object> params);
}

