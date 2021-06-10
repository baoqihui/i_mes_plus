package com.rh.i_mes_plus.mapper.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.ums.UmsCoItemsource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 物料类型
 * 
 * @author hqb
 * @date 2020-09-21 16:35:09
 */
@Mapper
public interface UmsCoItemsourceMapper extends SuperMapper<UmsCoItemsource> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<UmsCoItemsource> findList(Page<UmsCoItemsource> pages, @Param("p") Map<String, Object> params);
}
