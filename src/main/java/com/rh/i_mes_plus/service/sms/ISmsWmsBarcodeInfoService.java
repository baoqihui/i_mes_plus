package com.rh.i_mes_plus.service.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sms.SmsWmsBarcodeInfo;

import java.util.List;
import java.util.Map;

/**
 * 条码信息
 *
 * @author hqb
 * @date 2020-10-07 10:11:58
 */
public interface ISmsWmsBarcodeInfoService extends IService<SmsWmsBarcodeInfo> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<SmsWmsBarcodeInfo> findList(Map<String, Object> params);


    Result spilt(Map<String, Object> params);

    Result combine(Map<String, Object> params);

    Result getBarcodeInfo(Map<String, Object> params);

    Result getOpenBarcode();

    List<Map<String, Object>> getNotPdaBarcodes(String wrDocNum);
}

