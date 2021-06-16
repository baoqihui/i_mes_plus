package com.rh.i_mes_plus.service.impl.sms;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.sms.SmsWmsReloadDocDetailMapper;
import com.rh.i_mes_plus.model.sms.SmsWmsReloadDocDetail;
import com.rh.i_mes_plus.service.sms.ISmsWmsReloadDocDetailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 调拨单物料明细表
 *
 * @author hbq
 * @date 2020-12-16 14:55:14
 */
@Slf4j
@Service
public class SmsWmsReloadDocDetailServiceImpl extends ServiceImpl<SmsWmsReloadDocDetailMapper, SmsWmsReloadDocDetail> implements ISmsWmsReloadDocDetailService {
    @Resource
    private SmsWmsReloadDocDetailMapper smsWmsReloadDocDetailMapper;
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
        return smsWmsReloadDocDetailMapper.findList(pages, params);
    }
    
    @Override
    public List<Map<String,Object>> listAll(String reloadNo) {
        return smsWmsReloadDocDetailMapper.listAll(reloadNo);
    }
}
