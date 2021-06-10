package com.rh.i_mes_plus.mapper.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.ums.UmsAcceArea;
import com.rh.i_mes_plus.vo.ChildVO;
import com.rh.i_mes_plus.vo.OneVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 辅料储位
 *
 * @author hqb
 * @date 2020-09-22 13:34:31
 */
@Mapper
public interface UmsAcceAreaMapper extends SuperMapper<UmsAcceArea> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<UmsAcceArea> findList(Page<UmsAcceArea> pages, @Param("p") Map<String, Object> params);

    List<ChildVO> selectAllTree( String parentCode);
}
