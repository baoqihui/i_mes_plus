package com.rh.i_mes_plus.service.impl.sms;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.sms.SmsWmsMoveDocDetailSubMapper;
import com.rh.i_mes_plus.model.sms.SmsWmsMoveDocDetailSub;
import com.rh.i_mes_plus.service.sms.ISmsWmsMoveDocDetailSubService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 调拨单日志操作明细表
 *
 * @author hbq
 * @date 2020-12-11 13:39:17
 */
@Slf4j
@Service
public class SmsWmsMoveDocDetailSubServiceImpl extends ServiceImpl<SmsWmsMoveDocDetailSubMapper, SmsWmsMoveDocDetailSub> implements ISmsWmsMoveDocDetailSubService {
    @Resource
    private SmsWmsMoveDocDetailSubMapper smsWmsMoveDocDetailSubMapper;
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
        return smsWmsMoveDocDetailSubMapper.findList(pages, params);
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
        return smsWmsMoveDocDetailSubMapper.listAll(pages, params);
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
        return smsWmsMoveDocDetailSubMapper.listAllCollect(pages, params);
    }
}
