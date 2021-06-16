package com.rh.i_mes_plus.mapper.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.sms.SmsWmsMoveDoc;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 调拨单
 * 
 * @author hbq
 * @date 2020-12-11 13:39:17
 */
@Mapper
public interface SmsWmsMoveDocMapper extends SuperMapper<SmsWmsMoveDoc> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);
}
