package com.rh.i_mes_plus.service.impl.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.ums.UmsNotifyMapper;
import com.rh.i_mes_plus.model.ums.UmsNotify;
import com.rh.i_mes_plus.service.ums.IUmsNotifyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 用户通知表,包含了所有用户的消息
 *
 * @author hqb
 * @date 2020-09-17 08:34:13
 */
@Slf4j
@Service
public class UmsNotifyServiceImpl extends ServiceImpl<UmsNotifyMapper, UmsNotify> implements IUmsNotifyService {
    @Resource
    private UmsNotifyMapper umsNotifyMapper;
    /**
     * 列表
     * @param params
     * @return
     */
    public Page<UmsNotify> findList(Map<String, Object> params){
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<UmsNotify> pages = new Page<>(pageNum, pageSize);
        return umsNotifyMapper.findList(pages, params);
    }
}
