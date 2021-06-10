package com.rh.i_mes_plus.mapper.other;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.other.SapMesLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * sap-mes接口错误日志
 * 
 * @author hbq
 * @date 2020-10-31 10:58:31
 */
@Mapper
public interface SapMesLogMapper extends SuperMapper<SapMesLog> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);
}
