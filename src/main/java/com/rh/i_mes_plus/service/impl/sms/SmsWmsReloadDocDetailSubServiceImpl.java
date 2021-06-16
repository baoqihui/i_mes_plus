package com.rh.i_mes_plus.service.impl.sms;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.sms.SmsWmsReloadDocDetailSubMapper;
import com.rh.i_mes_plus.model.sms.SmsWmsReloadDocDetailSub;
import com.rh.i_mes_plus.service.sms.ISmsWmsReloadDocDetailSubService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 调拨单日志操作明细表
 *
 * @author hbq
 * @date 2020-12-16 14:55:14
 */
@Slf4j
@Service
public class SmsWmsReloadDocDetailSubServiceImpl extends ServiceImpl<SmsWmsReloadDocDetailSubMapper, SmsWmsReloadDocDetailSub> implements ISmsWmsReloadDocDetailSubService {
    @Resource
    private SmsWmsReloadDocDetailSubMapper smsWmsReloadDocDetailSubMapper;
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
        return smsWmsReloadDocDetailSubMapper.findList(pages, params);
    }
    
    @Override
    public Page<Map> listAll(Map<String, Object> params) {
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<Map> pages = new Page<>(pageNum, pageSize);
        return smsWmsReloadDocDetailSubMapper.listAll(pages, params);
    }
    
    @Override
    public Page<Map> listAllCollect(Map<String, Object> params) {
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<Map> pages = new Page<>(pageNum, pageSize);
        return smsWmsReloadDocDetailSubMapper.listAllCollect(pages, params);
    }

    @Override
    public List<Map<String, Object>> listByDid(String reloadDid) {
        return smsWmsReloadDocDetailSubMapper.listByDid(reloadDid);
    }
}
