package com.rh.i_mes_plus.service.gtoa;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.gtoa.EcnDetailInfoDTO;
import com.rh.i_mes_plus.dto.gtoa.EcnDetailInfoTwoDTO;
import com.rh.i_mes_plus.model.gtoa.EcnDetailInfo;

import java.util.Map;

/**
 * ECN详情表
 *
 * @author hbq
 * @date 2020-10-22 19:36:56
 */
public interface IEcnDetailInfoService extends IService<EcnDetailInfo> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Result saveOrUpdateAll(EcnDetailInfoDTO ecnDetailInfoDTO);

    Result audit(EcnDetailInfoTwoDTO ecnDetailInfoTwoDTO);

    Result perfect(EcnDetailInfo ecnDetailInfo);
}

