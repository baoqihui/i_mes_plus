package com.rh.i_mes_plus.service.impl.sms;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.sms.SmsWmsReceiveDetailSubMapper;
import com.rh.i_mes_plus.model.sms.SmsWmsReceiveDetailSub;
import com.rh.i_mes_plus.service.sms.ISmsWmsReceiveDetailSubService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author hbq
 * @date 2020-11-06 16:04:22
 */
@Slf4j
@Service
public class SmsWmsReceiveDetailSubServiceImpl extends ServiceImpl<SmsWmsReceiveDetailSubMapper, SmsWmsReceiveDetailSub> implements ISmsWmsReceiveDetailSubService {
    @Resource
    private SmsWmsReceiveDetailSubMapper smsWmsReceiveDetailSubMapper;
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
        return smsWmsReceiveDetailSubMapper.findList(pages, params);
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
        return smsWmsReceiveDetailSubMapper.listAll(pages, params);
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
        return smsWmsReceiveDetailSubMapper.listAllCollect(pages, params);
    }

    @Override
    public List<SmsWmsReceiveDetailSub> getGroup(String wrDocNum) {
        return smsWmsReceiveDetailSubMapper.getGroup(wrDocNum);
    }
}
