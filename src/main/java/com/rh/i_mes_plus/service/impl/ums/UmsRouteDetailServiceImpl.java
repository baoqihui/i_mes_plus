package com.rh.i_mes_plus.service.impl.ums;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.ums.UmsRouteDetailMapper;
import com.rh.i_mes_plus.model.ums.UmsRouteDetail;
import com.rh.i_mes_plus.service.ums.IUmsRouteDetailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 流程控制详情
 *
 * @author hbq
 * @date 2021-06-01 09:33:59
 */
@Slf4j
@Service
public class UmsRouteDetailServiceImpl extends ServiceImpl<UmsRouteDetailMapper, UmsRouteDetail> implements IUmsRouteDetailService {
    @Resource
    private UmsRouteDetailMapper umsRouteDetailMapper;
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
        return umsRouteDetailMapper.findList(pages, params);
    }
}
