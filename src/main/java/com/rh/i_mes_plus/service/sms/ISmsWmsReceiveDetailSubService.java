package com.rh.i_mes_plus.service.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.sms.SmsWmsReceiveDetailSub;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author hbq
 * @date 2020-11-06 16:04:22
 */
public interface ISmsWmsReceiveDetailSubService extends IService<SmsWmsReceiveDetailSub> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
    
    Page<Map> listAll(Map<String, Object> params);
    
    Page<Map> listAllCollect(Map<String, Object> params);

    List<SmsWmsReceiveDetailSub> getGroup(String wrDocNum);
}

