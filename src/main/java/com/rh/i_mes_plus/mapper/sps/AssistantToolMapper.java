package com.rh.i_mes_plus.mapper.sps;

import com.rh.i_mes_plus.model.sps.AssistantTool;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 辅料信息
 * 
 * @author hbq
 * @date 2021-07-08 19:48:01
 */
@Mapper
public interface AssistantToolMapper extends SuperMapper<AssistantTool> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);
}
