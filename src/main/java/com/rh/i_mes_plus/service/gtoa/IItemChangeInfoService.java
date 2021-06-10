package com.rh.i_mes_plus.service.gtoa;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.gtoa.ItemChangeInfo;

import java.util.Map;

/**
 * 变更项目表
 *
 * @author hbq
 * @date 2020-10-22 19:36:56
 */
public interface IItemChangeInfoService extends IService<ItemChangeInfo> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

