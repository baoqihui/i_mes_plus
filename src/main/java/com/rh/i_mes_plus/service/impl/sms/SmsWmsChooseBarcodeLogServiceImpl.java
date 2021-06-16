package com.rh.i_mes_plus.service.impl.sms;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.sms.SmsWmsChooseBarcodeLogMapper;
import com.rh.i_mes_plus.model.sms.SmsWmsChooseBarcodeLog;
import com.rh.i_mes_plus.service.sms.ISmsWmsChooseBarcodeLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 挑料日志表
 *
 * @author hbq
 * @date 2021-03-01 14:31:55
 */
@Slf4j
@Service
public class SmsWmsChooseBarcodeLogServiceImpl extends ServiceImpl<SmsWmsChooseBarcodeLogMapper, SmsWmsChooseBarcodeLog> implements ISmsWmsChooseBarcodeLogService {
    @Resource
    private SmsWmsChooseBarcodeLogMapper smsWmsChooseBarcodeLogMapper;
    /**
     * 列表
     * @param params
     * @return
     */
    public Page<Map> findList(Map<String, Object> params){
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<Map> pages = new Page<>(pageNum, pageSize);
        return smsWmsChooseBarcodeLogMapper.findList(pages, params);
    }
}
