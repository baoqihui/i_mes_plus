package com.rh.i_mes_plus.mapper.sms;

import com.rh.i_mes_plus.model.sms.SmsLightColor;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 仓库灯资源占用
 * 
 * @author hbq
 * @date 2021-06-24 15:19:55
 */
@Mapper
public interface SmsLightColorMapper extends SuperMapper<SmsLightColor> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<Map> pages, @Param("p") Map<String, Object> params);
}
