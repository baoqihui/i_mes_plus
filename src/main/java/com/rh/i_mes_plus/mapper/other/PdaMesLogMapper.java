package com.rh.i_mes_plus.mapper.other;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.other.PdaMesLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * pda与mes操作日志
 * 
 * @author hbq
 * @date 2021-01-28 17:56:25
 */
@Mapper
public interface PdaMesLogMapper extends SuperMapper<PdaMesLog> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);
}
