package com.rh.i_mes_plus.mapper.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.ums.UmsCustomerAddress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 客户地址
 * 
 * @author hqb
 * @date 2020-09-21 08:41:57
 */
@Mapper
public interface UmsCustomerAddressMapper extends SuperMapper<UmsCustomerAddress> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<UmsCustomerAddress> findList(Page<UmsCustomerAddress> pages, @Param("p") Map<String, Object> params);
}
