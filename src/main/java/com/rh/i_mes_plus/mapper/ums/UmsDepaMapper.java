package com.rh.i_mes_plus.mapper.ums;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.ums.UmsDepa;
import com.rh.i_mes_plus.model.ums.UmsUser;
import com.rh.i_mes_plus.vo.ChildVO;
import com.rh.i_mes_plus.vo.DepaTreeVO;
import com.rh.i_mes_plus.vo.OneVO;
import com.rh.i_mes_plus.vo.TwoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 部门
 * 
 * @author hqb
 * @date 2020-09-17 14:38:50
 */
@Mapper
public interface UmsDepaMapper extends SuperMapper<UmsDepa> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<UmsDepa> findList(Page<UmsDepa> pages, @Param("p") Map<String, Object> params);

    List<OneVO> treeList();

    List<UmsUser> getUserByDepaName(@Param("p")Map<String, Object> params);

    List<UmsUser> getNoUserByDepaName(@Param("p")Map<String, Object> params);

    List<TwoVO> treeOrgList();

    UmsDepa getParentDepa(String depaCode);

    List<ChildVO> selectAllTree(@Param("parentCode") String parentCode);

    List<ChildVO> selectOnlyDepaTree(String s);

    List<DepaTreeVO> selectTreeListByParentCode(@Param("parentCode") String parentCode);

    List<String> getDepaexclude();
}
