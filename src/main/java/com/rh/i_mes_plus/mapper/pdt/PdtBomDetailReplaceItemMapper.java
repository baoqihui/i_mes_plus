package com.rh.i_mes_plus.mapper.pdt;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.pdt.PdtBomDetailReplaceItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * bom详情替代料信息
 * 
 * @author hbq
 * @date 2021-06-09 20:13:20
 */
@Mapper
public interface PdtBomDetailReplaceItemMapper extends SuperMapper<PdtBomDetailReplaceItem> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);
}
