package com.rh.i_mes_plus.service.other;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.other.EccSendInfo;

import java.util.Map;

/**
 * ECC扣料数据
 *
 * @author hbq
 * @date 2021-01-09 15:34:09
 */
public interface IEccSendInfoService extends IService<EccSendInfo> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

