package com.rh.i_mes_plus.service.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.ums.UmsCoBarcodeRoul;

import java.util.Map;

/**
 * 物料条码规则
 *
 * @author hqb
 * @date 2020-09-21 16:35:09
 */
public interface IUmsCoBarcodeRoulService extends IService<UmsCoBarcodeRoul> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<UmsCoBarcodeRoul> findList(Map<String, Object> params);
}

