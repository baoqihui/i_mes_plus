package com.rh.i_mes_plus.mapper.ums;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.ums.UmsPermission;
import com.rh.i_mes_plus.vo.PermissionTreeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 权限表
 * 
 * @author hqb
 * @date 2020-09-12 16:31:21
 */
@Mapper
public interface UmsPermissionMapper extends SuperMapper<UmsPermission> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<UmsPermission> findList(Page<UmsPermission> pages, @Param("p") Map<String, Object> params);

    List<PermissionTreeVO> selectPermissionTreeByParentId(@Param("parentId") Long parentId);

    List<PermissionTreeVO> getUserPermission(@Param("userAccount") String userAccount,@Param("parentId") Long parentId);

    List<String>  getUserButtonPermission(@Param("userAccount") String userAccount);
}
