package com.rh.i_mes_plus.mapper.ums;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.ums.UmsUser;
import com.rh.i_mes_plus.vo.UmsPermissionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户表
 * 
 * @author hqb
 * @date 2020-09-12 16:31:21
 */
@Mapper
public interface UmsUserMapper extends SuperMapper<UmsUser> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param param
     * @return
     */
    Page<UmsUser> findList(Page<UmsUser> pages, @Param("p") Map<String, Object> params);

    List<UmsUser> getManager(@Param("p") Map<String, Object> params);

    List<UmsPermissionVO> selectPermissionOfUser(@Param("userId") Long userId);
}
