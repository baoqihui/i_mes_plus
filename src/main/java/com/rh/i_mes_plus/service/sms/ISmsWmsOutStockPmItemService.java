package com.rh.i_mes_plus.service.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.sms.SmsWmsOutStockPmItem;
import com.rh.i_mes_plus.vo.MassageVO;

import java.util.List;
import java.util.Map;

/**
 * 工单和备料单绑定表
 *
 * @author hbq
 * @date 2020-11-02 17:19:51
 */
public interface ISmsWmsOutStockPmItemService extends IService<SmsWmsOutStockPmItem> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    List<MassageVO> getMassageList(Long id);

    List<MassageVO> getMassageLotList(Long id);
}

