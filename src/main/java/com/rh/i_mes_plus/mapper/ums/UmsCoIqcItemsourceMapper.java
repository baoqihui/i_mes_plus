package com.rh.i_mes_plus.mapper.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.ums.UmsCoIqcItemsource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * IQC检验分类表
 * 
 * @author hqb
 * @date 2020-09-21 16:35:09
 */
@Mapper
public interface UmsCoIqcItemsourceMapper extends SuperMapper<UmsCoIqcItemsource> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<UmsCoIqcItemsource> findList(Page<UmsCoIqcItemsource> pages, @Param("p") Map<String, Object> params);
}
