package com.rh.i_mes_plus.service.iqc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.iqc.IqcTestItemType;

import java.util.Map;

/**
 * 检测项目类型
 *
 * @author hqb
 * @date 2020-10-16 10:06:18
 */
public interface IIqcTestItemTypeService extends IService<IqcTestItemType> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<IqcTestItemType> findList(Map<String, Object> params);
}

