package com.rh.i_mes_plus.mapper.gtoa;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.gtoa.TaskExecuteInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 任务执行状态
 *
 * @author hbq
 * @date 2020-10-29 18:10:24
 */
@Mapper
public interface TaskExecuteInfoMapper extends SuperMapper<TaskExecuteInfo> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);

    List<Map> getTaskExecuteInfo(String depaCode);
}
