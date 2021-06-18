package com.rh.i_mes_plus.mapper.pdt;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.pdt.PdtWmsOutStockDoc;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 成品出库单主表
 * 
 * @author hbq
 * @date 2021-01-06 15:22:23
 */
@Mapper
public interface PdtWmsOutStockDocMapper extends SuperMapper<PdtWmsOutStockDoc> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);

    List<String> getOutStockDocListByDtCode(@Param("p")Map<String, Object> map);

    List<Map<String, Object>> statement(@Param("p")Map<String, Object> map);

    List<Map<String, Object>> statementStock(@Param("modelCode") String modelCode);
}
