package com.rh.i_mes_plus.mapper.sps;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.sps.SpsSparesCheckDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 盘点明细表
 * 
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
@Mapper
public interface SpsSparesCheckDetailMapper extends SuperMapper<SpsSparesCheckDetail> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);

    List<Map<String, Object>> getGZBPDetailByCheckNo(@Param("checkNo") String checkNo);

    List<Map<String, Object>> getGWDetailByCheckNo(@Param("checkNo") String checkNo);

    List<Map<String, Object>> getLQDetailByCheckNo(@Param("checkNo") String checkNo);

    List<Map<String, Object>> getDefaultDetailByCheckNo(@Param("checkNo") String checkNo);

    List<Map<String, Object>> getGZZJDetailByCheckNo(@Param("checkNo") String checkNo);
}
