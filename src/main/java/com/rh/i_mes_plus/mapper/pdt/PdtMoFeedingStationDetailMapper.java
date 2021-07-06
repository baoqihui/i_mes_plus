package com.rh.i_mes_plus.mapper.pdt;

import com.rh.i_mes_plus.model.pdt.PdtMoFeedingStationDetail;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 
 * 
 * @author hbq
 * @date 2021-07-06 13:52:46
 */
@Mapper
public interface PdtMoFeedingStationDetailMapper extends SuperMapper<PdtMoFeedingStationDetail> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);
}
