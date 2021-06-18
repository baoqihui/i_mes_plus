package com.rh.i_mes_plus.mapper.pdt;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.other.EccMaterialDetail;
import com.rh.i_mes_plus.model.pdt.PdtWmsOutStockList;
import com.rh.i_mes_plus.vo.EccVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 成品出库明细表
 * 
 * @author hbq
 * @date 2021-01-06 15:22:23
 */
@Mapper
public interface PdtWmsOutStockListMapper extends SuperMapper<PdtWmsOutStockList> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);

    List<EccVO> ecc(@Param("docNo") String docNo);

    Integer getMaxSubGroup(@Param("mo")String mo);

    List<EccMaterialDetail> getMaterialList(@Param("mo") String mo, @Param("i") int i);

    Page<Map> listAll(Page<Map> pages, @Param("p") Map<String, Object> params);

    Page<Map> listAllCollect(Page<Map> pages, @Param("p") Map<String, Object> params);

    Page<Map> listAllCollectByBoxNo(Page<Map> pages,@Param("p") Map<String, Object> params);
}
