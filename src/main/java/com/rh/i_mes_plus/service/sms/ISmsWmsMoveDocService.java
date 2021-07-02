package com.rh.i_mes_plus.service.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.SmsWmsMoveDocDTO;
import com.rh.i_mes_plus.model.sms.SmsWmsMoveDoc;

import java.util.List;
import java.util.Map;

/**
 * 调拨单
 *
 * @author hbq
 * @date 2020-12-11 13:39:17
 */
public interface ISmsWmsMoveDocService extends IService<SmsWmsMoveDoc> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
    
    Result saveAll(SmsWmsMoveDocDTO smsWmsMoveDocDTO);
    
    Result getDocNo(Map<String, Object> map);
    
    Result removeAll(Map<String, List<Long>> map);
    
    Result pdaMove(Map<String, Object> map);

    Result lightUp(Map<String, Object> params);

    Result cancelLightUp(Map<String, Object> params);
}

