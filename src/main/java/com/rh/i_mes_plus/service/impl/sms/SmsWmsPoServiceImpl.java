package com.rh.i_mes_plus.service.impl.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.sms.SmsWmsPoMapper;
import com.rh.i_mes_plus.model.sms.SmsWmsPo;
import com.rh.i_mes_plus.service.sms.ISmsWmsPoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 采购订单
 *
 * @author hqb
 * @date 2020-09-29 15:58:30
 */
@Slf4j
@Service
public class SmsWmsPoServiceImpl extends ServiceImpl<SmsWmsPoMapper, SmsWmsPo> implements ISmsWmsPoService {
    @Resource
    private SmsWmsPoMapper smsWmsPoMapper;
    /**
     * 列表
     * @param params
     * @return
     */
    public Page<SmsWmsPo> findList(Map<String, Object> params){
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<SmsWmsPo> pages = new Page<>(pageNum, pageSize);
        return smsWmsPoMapper.findList(pages, params);
    }
}
