package com.rh.i_mes_plus.service.sps;

import com.rh.i_mes_plus.model.sps.AssistantTool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 辅料信息
 *
 * @author hbq
 * @date 2021-07-08 19:48:01
 */
public interface IAssistantToolService extends IService<AssistantTool> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);
}

