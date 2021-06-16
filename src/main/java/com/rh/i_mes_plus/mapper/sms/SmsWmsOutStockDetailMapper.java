package com.rh.i_mes_plus.mapper.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.sms.SmsWmsOutStockDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 出库物料表
 * 
 * @author hbq
 * @date 2020-11-02 14:32:34
 */
@Mapper
public interface SmsWmsOutStockDetailMapper extends SuperMapper<SmsWmsOutStockDetail> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);
    
    List<Map<String, Object>> getListByDocNo(@Param("p")Map<String, Object> map);
}
