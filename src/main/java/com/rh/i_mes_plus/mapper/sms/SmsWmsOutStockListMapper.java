package com.rh.i_mes_plus.mapper.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.sms.SmsWmsOutStockList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 出库明细表
 * 
 * @author hbq
 * @date 2020-11-02 14:32:34
 */
@Mapper
public interface SmsWmsOutStockListMapper extends SuperMapper<SmsWmsOutStockList> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);
    
    Page<Map> listAll(Page<Map> pages, @Param("p") Map<String, Object> params);
    
    Page<Map> listAllCollect(Page<Map> pages,@Param("p") Map<String, Object> params);

    List<Map<String, Object>> listAndBarcode(@Param("osdId") String osdId);
}
