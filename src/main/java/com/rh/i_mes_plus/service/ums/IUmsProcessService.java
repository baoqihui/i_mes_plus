package com.rh.i_mes_plus.service.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.ums.UmsProcess;

import java.util.Map;

/**
 * 工序管理
 *
 * @author hbq
 * @date 2021-05-20 15:31:14
 */
public interface IUmsProcessService extends IService<UmsProcess> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

