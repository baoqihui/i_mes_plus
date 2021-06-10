package com.rh.i_mes_plus.mapper.gtoa;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.gtoa.VerControlDelivery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 不允许发货版本控制
 * 
 * @author hbq
 * @date 2020-11-02 18:30:05
 */
@Mapper
public interface VerControlDeliveryMapper extends SuperMapper<VerControlDelivery> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);
}
