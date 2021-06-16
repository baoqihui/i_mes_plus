package com.rh.i_mes_plus.mapper.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.sms.SmsWmsPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 采购订单
 * 
 * @author hqb
 * @date 2020-09-29 15:58:30
 */
@Mapper
public interface SmsWmsPoMapper extends SuperMapper<SmsWmsPo> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<SmsWmsPo> findList(Page<SmsWmsPo> pages, @Param("p") Map<String, Object> params);
}
