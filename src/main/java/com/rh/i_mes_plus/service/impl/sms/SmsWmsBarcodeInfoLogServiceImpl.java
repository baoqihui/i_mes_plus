package com.rh.i_mes_plus.service.impl.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.sms.SmsWmsBarcodeInfoLogMapper;
import com.rh.i_mes_plus.model.sms.SmsWmsBarcodeInfoLog;
import com.rh.i_mes_plus.service.sms.ISmsWmsBarcodeInfoLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 条码信息(临时表)
 *
 * @author hqb
 * @date 2020-10-07 14:27:41
 */
@Slf4j
@Service
public class SmsWmsBarcodeInfoLogServiceImpl extends ServiceImpl<SmsWmsBarcodeInfoLogMapper, SmsWmsBarcodeInfoLog> implements ISmsWmsBarcodeInfoLogService {
    @Resource
    private SmsWmsBarcodeInfoLogMapper smsWmsBarcodeInfoLogMapper;
    /**
     * 列表
     * @param params
     * @return
     */
    public Page<SmsWmsBarcodeInfoLog> findList(Map<String, Object> params){
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<SmsWmsBarcodeInfoLog> pages = new Page<>(pageNum, pageSize);
        return smsWmsBarcodeInfoLogMapper.findList(pages, params);
    }
}
