package com.rh.i_mes_plus.mapper.pdt;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.pdt.PdtWmsReceiveDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 入库单明细表
 * 
 * @author hbq
 * @date 2020-12-29 15:41:49
 */
@Mapper
public interface PdtWmsReceiveDetailMapper extends SuperMapper<PdtWmsReceiveDetail> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);

    List<Map<String, Object>> listAll(@Param("docNo") String docNo);
}
