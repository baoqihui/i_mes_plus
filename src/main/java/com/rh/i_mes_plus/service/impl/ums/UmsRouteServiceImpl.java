package com.rh.i_mes_plus.service.impl.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.ums.UmsRouteMapper;
import com.rh.i_mes_plus.model.ums.UmsRoute;
import com.rh.i_mes_plus.service.ums.IUmsRouteService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 流程控制
 *
 * @author hbq
 * @date 2021-06-01 09:33:59
 */
@Slf4j
@Service
public class UmsRouteServiceImpl extends ServiceImpl<UmsRouteMapper, UmsRoute> implements IUmsRouteService {
    @Resource
    private UmsRouteMapper umsRouteMapper;
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
        return umsRouteMapper.findList(pages, params);
    }
}
