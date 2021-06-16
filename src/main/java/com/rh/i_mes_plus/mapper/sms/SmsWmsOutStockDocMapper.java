package com.rh.i_mes_plus.mapper.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.sms.SmsWmsOutStockDoc;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 出库单主表
 * 
 * @author hbq
 * @date 2020-11-02 14:32:34
 */
@Mapper
public interface SmsWmsOutStockDocMapper extends SuperMapper<SmsWmsOutStockDoc> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);
    
    List<Map<String,Object>> getDocNoByProjectIdAndDocNo(@Param("p") Map<String, Object> map);
    
    List<Map<String, Object>> getItemNumByDocNos(@Param("p")Map<String, List<String>> map);
    
    String getAllProjects(@Param("p") Map<String, List<String>> map);
    
    List<Map<String, Object>> statement(@Param("p")Map<String, Object> map);
    
    List<Map<String,Object>> statementStock(@Param("coItemCode")String coItemCode);
}
