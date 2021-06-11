package com.rh.i_mes_plus.mapper.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.ums.UmsItemMes;
import com.rh.i_mes_plus.model.ums.UmsItemSap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 物料信息（sap获取）
 * 
 * @author hqb
 * @date 2020-09-21 11:13:27
 */
@Mapper
public interface UmsItemSapMapper extends SuperMapper<UmsItemSap> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<UmsItemSap> pages, @Param("p") Map<String, Object> params);

    Page<UmsItemMes> findList2(Page<UmsItemSap> pages,@Param("p") Map<String, Object> params);
}
