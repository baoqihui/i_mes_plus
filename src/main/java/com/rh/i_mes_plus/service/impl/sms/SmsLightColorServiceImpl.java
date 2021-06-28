package com.rh.i_mes_plus.service.impl.sms;
import com.rh.i_mes_plus.mapper.sms.SmsLightColorMapper;
import com.rh.i_mes_plus.service.sms.ISmsLightColorService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import lombok.extern.slf4j.Slf4j;

import com.rh.i_mes_plus.model.sms.SmsLightColor;

/**
 * 仓库灯资源占用
 *
 * @author hbq
 * @date 2021-06-24 15:19:55
 */
@Slf4j
@Service
public class SmsLightColorServiceImpl extends ServiceImpl<SmsLightColorMapper, SmsLightColor> implements ISmsLightColorService {
    @Resource
    private SmsLightColorMapper smsLightColorMapper;
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
        return smsLightColorMapper.findList(pages, params);
    }
}
