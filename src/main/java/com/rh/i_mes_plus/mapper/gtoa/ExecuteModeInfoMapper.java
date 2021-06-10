package com.rh.i_mes_plus.mapper.gtoa;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.gtoa.ExecuteModeInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 执行方式表
 * 
 * @author hbq
 * @date 2020-10-22 19:36:56
 */
@Mapper
public interface ExecuteModeInfoMapper extends SuperMapper<ExecuteModeInfo> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);
}
