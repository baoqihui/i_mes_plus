package com.rh.i_mes_plus.service.pdt;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.pdt.PdtBomDetailReplaceItem;

import java.util.Map;

/**
 * bom详情替代料信息
 *
 * @author hbq
 * @date 2021-06-09 20:13:20
 */
public interface IPdtBomDetailReplaceItemService extends IService<PdtBomDetailReplaceItem> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

