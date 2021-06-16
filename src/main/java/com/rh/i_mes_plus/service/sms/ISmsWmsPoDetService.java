package com.rh.i_mes_plus.service.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.SmsWmsBarcodeInfoDTO;
import com.rh.i_mes_plus.dto.UmsPoDTO;
import com.rh.i_mes_plus.model.sms.SmsWmsPoDet;

import java.util.List;
import java.util.Map;

/**
 * 采购订单明细
 *
 * @author hqb
 * @date 2020-09-29 15:58:30
 */
public interface ISmsWmsPoDetService extends IService<SmsWmsPoDet> {
    /**
     * 列表
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Result createBarCode(List<SmsWmsBarcodeInfoDTO> smsWmsBarcodeInfoDTOS);

    Result saveAll(List<UmsPoDTO> umsPoDTO);

    Result createSingleBarCode(Map<String, Object> params);
}

