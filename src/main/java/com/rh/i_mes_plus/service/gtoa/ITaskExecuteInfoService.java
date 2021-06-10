package com.rh.i_mes_plus.service.gtoa;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rh.i_mes_plus.common.model.Result;
import com.rh.i_mes_plus.dto.TaskExecuteInfoDTO;
import com.rh.i_mes_plus.model.gtoa.TaskExecuteInfo;

import java.util.List;
import java.util.Map;

/**
 * 任务执行状态
 *
 * @author hbq
 * @date 2020-10-29 18:10:24
 */
public interface ITaskExecuteInfoService extends IService<TaskExecuteInfo> {
    /**
     * 列表
     * @param params
     * @return
     */
    Page<Map> findList(Map<String, Object> params);

    Result execute(TaskExecuteInfoDTO taskExecuteInfoDTO);

    Result audit(TaskExecuteInfo taskExecuteInfo);

    Result saveAll(List<TaskExecuteInfo> taskExecuteInfos);

    List<Map> getTaskExecuteInfo(String depaCode);
}

