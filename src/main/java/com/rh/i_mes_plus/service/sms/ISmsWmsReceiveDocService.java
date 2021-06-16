package com.rh.i_mes_plus.service.sms;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.SmsWmsReceiveAllDTO;
import com.rh.i_mes_plus.model.sms.SmsWmsReceiveDoc;

import java.util.List;
import java.util.Map;

/**
 * 采购入库单表
 *
 * @author hqb
 * @date 2020-10-07 15:25:51
 */
public interface ISmsWmsReceiveDocService extends IService<SmsWmsReceiveDoc> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Result saveAll(SmsWmsReceiveAllDTO smsWmsReceiveAllDTO);

    Result updateAll(SmsWmsReceiveAllDTO smsWmsReceiveAllDTO);

    Result deleteAll(Map<String, List<String>> map);

    Page<Map> allList(Map<String, Object> params);

    Page<Map> allSelectList(Map<String, Object> params);

    Result otherWarehouseSave(SmsWmsReceiveAllDTO smsWmsReceiveAllDTO);

    Result otherWarehouseUpdate(SmsWmsReceiveAllDTO smsWmsReceiveAllDTO);

    Result pdaReceive(Map<String, Object> map);
    
    List<String> getReceiveList(Map<String, Object> map);
    
    Result getDocNum(Map<String, Object> map);
    
    String getNewDocNum(String prefix);

    Result close(Map<String, Object> map);

    Result pdaReceiveCancel(Map<String, Object> map);

    JSONObject getInfoBySn(Map<String, Object> params);
}

