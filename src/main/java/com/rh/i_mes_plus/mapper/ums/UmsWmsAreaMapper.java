package com.rh.i_mes_plus.mapper.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.ums.UmsWmsArea;
import com.rh.i_mes_plus.vo.OneVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author hqb
 * @date 2020-09-24 13:06:30
 */
@Mapper
public interface UmsWmsAreaMapper extends SuperMapper<UmsWmsArea> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);

    List<OneVO> treeList(@Param("p") Map<String, Object> params);

    UmsWmsArea getParent(String arSn);

    List<String> findAllMainBoard();

    List<UmsWmsArea> getEmptyStock();
}
