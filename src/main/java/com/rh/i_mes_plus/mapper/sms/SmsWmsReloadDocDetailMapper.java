package com.rh.i_mes_plus.mapper.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.sms.SmsWmsReloadDocDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 调拨单物料明细表
 * 
 * @author hbq
 * @date 2020-12-16 14:55:14
 */
@Mapper
public interface SmsWmsReloadDocDetailMapper extends SuperMapper<SmsWmsReloadDocDetail> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);
    
    List<Map<String,Object>> listAll(@Param("reloadNo") String reloadNo);
}
