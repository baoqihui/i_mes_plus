package com.rh.i_mes_plus.mapper.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.sms.SmsWmsReloadDocDetailSub;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 调拨单日志操作明细表
 * 
 * @author hbq
 * @date 2020-12-16 14:55:14
 */
@Mapper
public interface SmsWmsReloadDocDetailSubMapper extends SuperMapper<SmsWmsReloadDocDetailSub> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);
    
    Page<Map> listAll(Page<Map> pages,@Param("p")  Map<String, Object> params);
    
    Page<Map> listAllCollect(Page<Map> pages,@Param("p") Map<String, Object> params);

    List<Map<String, Object>> listByDid(@Param("reloadDid") String reloadDid);
}
