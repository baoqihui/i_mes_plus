package com.rh.i_mes_plus.mapper.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rh.i_mes_plus.common.mapper.SuperMapper;
import com.rh.i_mes_plus.model.sms.SmsWmsBarcodeInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 条码信息
 * 
 * @author hqb
 * @date 2020-10-07 10:11:58
 */
@Mapper
public interface SmsWmsBarcodeInfoMapper extends SuperMapper<SmsWmsBarcodeInfo> {
    /**
     * 分页查询用户列表
     * @param pages
     * @param params
     * @return
     */
    Page<SmsWmsBarcodeInfo> findList(Page<SmsWmsBarcodeInfo> pages, @Param("p") Map<String, Object> params);


    List<Map<String, Object>> getNotPdaBarcodes(@Param("wrDocNum") String wrDocNum);
}
