package com.rh.i_mes_plus.service.impl.sms;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.sms.SmsWmsOutStockDetailMapper;
import com.rh.i_mes_plus.model.sms.SmsWmsOutStockDetail;
import com.rh.i_mes_plus.service.sms.ISmsWmsOutStockDetailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 出库物料表
 *
 * @author hbq
 * @date 2020-11-02 14:32:34
 */
@Slf4j
@Service
public class SmsWmsOutStockDetailServiceImpl extends ServiceImpl<SmsWmsOutStockDetailMapper, SmsWmsOutStockDetail> implements ISmsWmsOutStockDetailService {
    @Resource
    private SmsWmsOutStockDetailMapper smsWmsOutStockDetailMapper;
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
        return smsWmsOutStockDetailMapper.findList(pages, params);
    }
    
    @Override
    public List<Map<String, Object>> getListByDocNo(Map<String, Object> map) {
        return smsWmsOutStockDetailMapper.getListByDocNo(map);
    }
}
