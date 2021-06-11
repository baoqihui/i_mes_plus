package com.rh.i_mes_plus.service.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.ums.UmsTechnicsPath;

import java.util.Map;

/**
 * 工艺路线
 *
 * @author hbq
 * @date 2021-06-01 10:57:04
 */
public interface IUmsTechnicsPathService extends IService<UmsTechnicsPath> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

