package com.rh.i_mes_plus.service.impl.sms;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.sms.SmsWmsOutStockListMapper;
import com.rh.i_mes_plus.model.sms.SmsWmsOutStockList;
import com.rh.i_mes_plus.service.sms.ISmsWmsOutStockListService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 出库明细表
 *
 * @author hbq
 * @date 2020-11-02 14:32:34
 */
@Slf4j
@Service
public class SmsWmsOutStockListServiceImpl extends ServiceImpl<SmsWmsOutStockListMapper, SmsWmsOutStockList> implements ISmsWmsOutStockListService {
    @Resource
    private SmsWmsOutStockListMapper smsWmsOutStockListMapper;
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
        return smsWmsOutStockListMapper.findList(pages, params);
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
        return smsWmsOutStockListMapper.listAll(pages, params);
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
        return smsWmsOutStockListMapper.listAllCollect(pages, params);
    }

    @Override
    public List<Map<String, Object>> listAndBarcode(String osdId) {
        return smsWmsOutStockListMapper.listAndBarcode(osdId);
    }
}
