package com.rh.i_mes_plus.mapper.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.sms.SmsWmsIoType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 单据类型表
 * 
 * @author hbq
 * @date 2021-01-19 09:52:44
 */
@Mapper
public interface SmsWmsIoTypeMapper extends SuperMapper<SmsWmsIoType> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);
}
