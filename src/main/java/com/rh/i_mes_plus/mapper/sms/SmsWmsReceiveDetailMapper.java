package com.rh.i_mes_plus.mapper.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.sms.SmsWmsReceiveDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 
 * 
 * @author hqb
 * @date 2020-10-07 15:25:52
 */
@Mapper
public interface SmsWmsReceiveDetailMapper extends SuperMapper<SmsWmsReceiveDetail> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<SmsWmsReceiveDetail> findList(Page<SmsWmsReceiveDetail> pages, @Param("p") Map<String, Object> params);
}
