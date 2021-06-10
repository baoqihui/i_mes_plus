package com.rh.i_mes_plus.service.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.ums.UmsCustomerGrade;

import java.util.Map;

/**
 * 客户等级（暂时不用）
 *
 * @author hqb
 * @date 2020-09-21 08:41:57
 */
public interface IUmsCustomerGradeService extends IService<UmsCustomerGrade> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<UmsCustomerGrade> findList(Map<String, Object> params);
}

