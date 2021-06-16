package com.rh.i_mes_plus.mapper.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.sms.SmsWmsReceiveDetailSub;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author hbq
 * @date 2020-11-06 16:04:22
 */
@Mapper
public interface SmsWmsReceiveDetailSubMapper extends SuperMapper<SmsWmsReceiveDetailSub> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);
    
    Page<Map> listAll(Page<Map> pages,@Param("p") Map<String, Object> params);
    
    Page<Map> listAllCollect(Page<Map> pages,@Param("p") Map<String, Object> params);

    List<SmsWmsReceiveDetailSub> getGroup(@Param("wrDocNum") String wrDocNum);
}
