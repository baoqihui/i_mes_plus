package com.rh.i_mes_plus.mapper.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.ums.UmsCustomer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 客户管理表
 * 
 * @author hqb
 * @date 2020-09-21 08:41:57
 */
@Mapper
public interface UmsCustomerMapper extends SuperMapper<UmsCustomer> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<UmsCustomer> findList(Page<UmsCustomer> pages, @Param("p") Map<String, Object> params);
}
