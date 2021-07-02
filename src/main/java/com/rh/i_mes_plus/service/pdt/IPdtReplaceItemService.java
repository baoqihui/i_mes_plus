package com.rh.i_mes_plus.service.pdt;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.pdt.PdtReplaceItem;

import java.util.List;
import java.util.Map;

/**
 * 替代料
 *
 * @author hbq
 * @date 2021-03-30 13:20:00
 */
public interface IPdtReplaceItemService extends IService<PdtReplaceItem> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Result presentToBefore(Map<String,Object> map);

    Result presentToAfter(Map<String,Object> map);

    Result head(Map<String, Object> map);

    Result tail(Map<String, Object> map);

    Result exchange(Map<String, Object> map);

    List<PdtReplaceItem> getGroupItemListByItemCode(String itemCode,String modelCode, int isExcludeItself);

    Result updateBomByReplaceGroup(PdtReplaceItem pdtReplaceItem);
}

