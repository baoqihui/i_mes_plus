package com.rh.i_mes_plus.mapper.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.ums.UmsLineBody;
import com.rh.i_mes_plus.vo.ChildVO;
import com.rh.i_mes_plus.vo.OneVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 线别管理
 * 
 * @author hbq
 * @date 2021-05-20 15:31:14
 */
@Mapper
public interface UmsLineBodyMapper extends SuperMapper<UmsLineBody> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);

    List<OneVO> treeList(@Param("params") Map<String, Object> params);

    List<ChildVO> selectAllTree(String parentCode);
}
