package com.rh.i_mes_plus.mapper.ums;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.ums.UmsUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 用户角色关联表
 * 
 * @author hqb
 * @date 2020-09-12 16:31:21
 */
@Mapper
public interface UmsUserRoleMapper extends SuperMapper<UmsUserRole> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<UmsUserRole> findList(Page<UmsUserRole> pages, @Param("p") Map<String, Object> params);
}
