package com.rh.i_mes_plus.service.impl.sms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.sms.SmsWmsOutStockPmItemMapper;
import com.rh.i_mes_plus.model.sms.SmsWmsOutStockPmItem;
import com.rh.i_mes_plus.service.sms.ISmsWmsOutStockPmItemService;
import com.rh.i_mes_plus.vo.MassageVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 工单和备料单绑定表
 *
 * @author hbq
 * @date 2020-11-02 17:19:51
 */
@Slf4j
@Service
public class SmsWmsOutStockPmItemServiceImpl extends ServiceImpl<SmsWmsOutStockPmItemMapper, SmsWmsOutStockPmItem> implements ISmsWmsOutStockPmItemService {
    @Resource
    private SmsWmsOutStockPmItemMapper smsWmsOutStockPmItemMapper;
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
        return smsWmsOutStockPmItemMapper.findList(pages, params);
    }

    @Override
    public List<MassageVO> getMassageList(Long id) {
        return smsWmsOutStockPmItemMapper.getMassageList(id);
    }

    @Override
    public List<MassageVO> getMassageLotList(Long id) {
        return smsWmsOutStockPmItemMapper.getMassageLotList(id);
    }
}
