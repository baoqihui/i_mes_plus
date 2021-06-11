package com.rh.i_mes_plus.service.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.ums.UmsSparesTypeInfo;

import java.util.Map;

/**
 * 备品大类信息表
 *
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
public interface IUmsSparesTypeInfoService extends IService<UmsSparesTypeInfo> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

