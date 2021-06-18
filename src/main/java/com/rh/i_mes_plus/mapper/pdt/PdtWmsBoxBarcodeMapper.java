package com.rh.i_mes_plus.mapper.pdt;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.pdt.PdtWmsBoxBarcode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author hbq
 * @date 2020-12-28 13:28:45
 */
@Mapper
public interface PdtWmsBoxBarcodeMapper extends SuperMapper<PdtWmsBoxBarcode> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);

    Map<String, Object> selBoxBarcode(@Param("boxNo")String boxNo);

    List<Map<String, Object>> listByBoxNo(@Param("boxNo") String boxNo);
}
