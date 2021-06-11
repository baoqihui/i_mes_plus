package com.rh.i_mes_plus.mapper.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.ums.UmsSupplier;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * BOM料件供应商表
 * 
 * @author hqb
 * @date 2020-09-19 14:42:47
 */
@Mapper
public interface UmsSupplierMapper extends SuperMapper<UmsSupplier> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<UmsSupplier> findList(Page<UmsSupplier> pages, @Param("p") Map<String, Object> params);
}
