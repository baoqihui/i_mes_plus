package com.rh.i_mes_plus.service.iqc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.iqc.IqcOqaTestGrade;

import java.util.Map;

/**
 * 检验水平
 *
 * @author hqb
 * @date 2020-10-16 13:20:30
 */
public interface IIqcOqaTestGradeService extends IService<IqcOqaTestGrade> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<IqcOqaTestGrade> findList(Map<String, Object> params);
}

