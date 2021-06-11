package com.rh.i_mes_plus.mapper.other;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.dto.ProjectAndFaceDTO;
import com.rh.i_mes_plus.model.other.WmsProjectDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 工单明细表
 * 
 * @author hbq
 * @date 2020-10-30 11:19:39
 */
@Mapper
public interface WmsProjectDetailMapper extends SuperMapper<WmsProjectDetail> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);

    List<Map<String, Object>> getDetailByProjectIdsAndFace(@Param("p")ProjectAndFaceDTO model);
}
