package com.rh.i_mes_plus.mapper.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.sms.SmsWmsStockInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 库存信息表(修改此表：如果在库数量为0时，需要把相关信息保存至表T_WMS_STOCK_ZERO_INFO，退料时才能得到SN原有的相关信息)
 * 
 * @author hbq
 * @date 2020-12-09 10:11:55
 */
@Mapper
public interface SmsWmsStockInfoMapper extends SuperMapper<SmsWmsStockInfo> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);
    
    Page<Map> listAll(Page<Map> pages, @Param("p") Map<String, Object> params);
    
    Page<Map> listAllCollect(Page<Map> pages,@Param("p")  Map<String, Object> params);
}
