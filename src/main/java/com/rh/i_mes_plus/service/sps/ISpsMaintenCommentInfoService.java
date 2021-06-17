package com.rh.i_mes_plus.service.sps;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.model.sps.SpsMaintenCommentInfo;

import java.util.Map;

/**
 * 保养内容信息表
 *
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
public interface ISpsMaintenCommentInfoService extends IService<SpsMaintenCommentInfo> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Result saveAll(SpsMaintenCommentInfo spsMaintenCommentInfo);
}

