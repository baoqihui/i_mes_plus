package com.rh.i_mes_plus.service.sps;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.sps.ApprovalProcess;

import java.util.Map;

/**
 * 流程控制
 *
 * @author hbq
 * @date 2021-02-21 16:06:24
 */
public interface IApprovalProcessService extends IService<ApprovalProcess> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    ApprovalProcess getOperUsrNo(Map<String, Object> params);
}

