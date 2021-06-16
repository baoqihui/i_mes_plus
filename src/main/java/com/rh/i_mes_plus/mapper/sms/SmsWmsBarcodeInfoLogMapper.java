package com.rh.i_mes_plus.mapper.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.sms.SmsWmsBarcodeInfoLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 条码信息(临时表)
 * 
 * @author hqb
 * @date 2020-10-07 14:27:41
 */
@Mapper
public interface SmsWmsBarcodeInfoLogMapper extends SuperMapper<SmsWmsBarcodeInfoLog> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<SmsWmsBarcodeInfoLog> findList(Page<SmsWmsBarcodeInfoLog> pages, @Param("p") Map<String, Object> params);

    String getMax(@Param("itemCode") String itemCode,@Param("yyyyMMdd") String yyyyMMdd);
}
