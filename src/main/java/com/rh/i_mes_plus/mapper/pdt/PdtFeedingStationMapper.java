package com.rh.i_mes_plus.mapper.pdt;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.pdt.PdtFeedingStation;
import com.rh.i_mes_plus.model.pdt.PdtFeedingStationDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 料站表主表
 * 
 * @author hbq
 * @date 2021-05-24 10:17:57
 */
@Mapper
public interface PdtFeedingStationMapper extends SuperMapper<PdtFeedingStation> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);
}
