package com.rh.i_mes_plus.mapper.ums;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.ums.UmsRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 角色表
 * 
 * @author hqb
 * @date 2020-09-12 16:31:21
 */
@Mapper
public interface UmsRoleMapper extends SuperMapper<UmsRole> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<UmsRole> pages, @Param("p") Map<String, Object> params);

    List<UmsRole> getRoleListByUid(@Param("UserId") Long id);
}
