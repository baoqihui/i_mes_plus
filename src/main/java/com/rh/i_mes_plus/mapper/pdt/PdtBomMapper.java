package com.rh.i_mes_plus.mapper.pdt;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.pdt.PdtBom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * bom表
 * 
 * @author hbq
 * @date 2021-05-25 13:18:22
 */
@Mapper
public interface PdtBomMapper extends SuperMapper<PdtBom> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);

    Map<String, Object> getPcbMapByItemCode(@Param("p") Map<String, Object> params);
}
