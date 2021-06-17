package com.rh.i_mes_plus.service.sps;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.sps.GzkPartsDetailInfo;
import com.rh.i_mes_plus.vo.GzkPartsDetailInfoVO;

import java.util.List;
import java.util.Map;

/**
 * 工装备品详情信息表
 *
 * @author hbq
 * @date 2021-02-21 10:39:50
 */
public interface IGzkPartsDetailInfoService extends IService<GzkPartsDetailInfo> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Page<Map> statement(Map<String, Object> params);

    List<GzkPartsDetailInfoVO> leadOutByIds(List<Long> ids);
}

