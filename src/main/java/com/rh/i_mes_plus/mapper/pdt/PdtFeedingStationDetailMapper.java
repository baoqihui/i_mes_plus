package com.rh.i_mes_plus.mapper.pdt;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.pdt.PdtFeedingStationDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 料站表详情
 * 
 * @author hbq
 * @date 2021-05-24 10:17:57
 */
@Mapper
public interface PdtFeedingStationDetailMapper extends SuperMapper<PdtFeedingStationDetail> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);
}
