package com.rh.i_mes_plus.service.impl.sms;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.sms.SmsWmsErpDocTypeMapper;
import com.rh.i_mes_plus.model.sms.SmsWmsErpDocType;
import com.rh.i_mes_plus.service.sms.ISmsWmsErpDocTypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * ERP单据性质档
 *
 * @author hbq
 * @date 2021-01-19 09:52:44
 */
@Slf4j
@Service
public class SmsWmsErpDocTypeServiceImpl extends ServiceImpl<SmsWmsErpDocTypeMapper, SmsWmsErpDocType> implements ISmsWmsErpDocTypeService {
    @Resource
    private SmsWmsErpDocTypeMapper smsWmsErpDocTypeMapper;
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
        return smsWmsErpDocTypeMapper.findList(pages, params);
    }
}
