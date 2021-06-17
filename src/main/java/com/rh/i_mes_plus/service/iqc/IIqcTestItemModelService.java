package com.rh.i_mes_plus.service.iqc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.iqc.IqcTestItemModel;

import java.util.Map;

/**
 * 机种检验项目
 *
 * @author hqb
 * @date 2020-10-16 16:35:54
 */
public interface IIqcTestItemModelService extends IService<IqcTestItemModel> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

