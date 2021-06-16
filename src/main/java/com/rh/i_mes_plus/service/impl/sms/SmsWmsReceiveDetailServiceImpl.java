package com.rh.i_mes_plus.service.impl.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.sms.SmsWmsReceiveDetailMapper;
import com.rh.i_mes_plus.model.sms.SmsWmsReceiveDetail;
import com.rh.i_mes_plus.service.sms.ISmsWmsReceiveDetailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 
 *
 * @author hqb
 * @date 2020-10-07 15:25:52
 */
@Slf4j
@Service
public class SmsWmsReceiveDetailServiceImpl extends ServiceImpl<SmsWmsReceiveDetailMapper, SmsWmsReceiveDetail> implements ISmsWmsReceiveDetailService {
    @Resource
    private SmsWmsReceiveDetailMapper smsWmsReceiveDetailMapper;
    /**
     * 列表
     * @param params
     * @return
     */
    public Page<SmsWmsReceiveDetail> findList(Map<String, Object> params){
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<SmsWmsReceiveDetail> pages = new Page<>(pageNum, pageSize);
        return smsWmsReceiveDetailMapper.findList(pages, params);
    }
}
