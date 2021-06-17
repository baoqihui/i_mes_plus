package com.rh.i_mes_plus.service.iqc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.iqc.IqcOqa;
import com.rh.i_mes_plus.model.iqc.IqcOqaSingleValue;

import java.util.List;
import java.util.Map;

/**
 * 检验单
 *
 * @author hbq
 * @date 2020-10-21 14:29:19
 */
public interface IIqcOqaService extends IService<IqcOqa> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Result saveOqaAndBath(Map<String, Object> map);

    Result checkList(String oqcNo);

    Result oqaBath(String oqcNo);

    Result oqaHistory(String oqcNo);

    Result oqaSingle(String oqcNo);

    Result oqaSingleValue(Map<String, Object> params);

    Result createProject(String oqcNo);

    Result createSample(Map<String,Object> map);

    Result removeSample(Map<String, List<Long>> map);

    Result updateSingleValue(Map<String, List<IqcOqaSingleValue>> map);
}

