package com.rh.i_mes_plus.service.gtoa;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.model.gtoa.TaskBaseInfo;

import java.util.Map;

/**
 * 任务信息
 *
 * @author hbq
 * @date 2020-10-29 18:10:24
 */
public interface ITaskBaseInfoService extends IService<TaskBaseInfo> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Integer getDepaCount();
}

