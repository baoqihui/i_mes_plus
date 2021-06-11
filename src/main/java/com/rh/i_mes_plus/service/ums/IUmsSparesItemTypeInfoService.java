package com.rh.i_mes_plus.service.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.ums.UmsSparesItemTypeInfo;

import java.util.Map;

/**
 * 备品类别信息表
 *
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
public interface IUmsSparesItemTypeInfoService extends IService<UmsSparesItemTypeInfo> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Result getItemTypeCodeByTypeCode(Map<String, Object> map);
}

