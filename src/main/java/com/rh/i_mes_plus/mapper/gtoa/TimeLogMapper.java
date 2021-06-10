package com.rh.i_mes_plus.mapper.gtoa;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.gtoa.TimeLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 时间日志
 * 
 * @author hbq
 * @date 2020-11-03 19:54:46
 */
@Mapper
public interface TimeLogMapper extends SuperMapper<TimeLog> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);
}
