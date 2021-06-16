package com.rh.i_mes_plus.mapper.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.sms.SmsWmsPoDet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 采购订单明细
 * 
 * @author hqb
 * @date 2020-09-29 15:58:30
 */
@Mapper
public interface SmsWmsPoDetMapper extends SuperMapper<SmsWmsPoDet> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<Map> findList(Page<SmsWmsPoDet> pages, @Param("p") Map<String, Object> params);
}
