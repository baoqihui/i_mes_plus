package com.rh.i_mes_plus.service.impl.ums;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.ums.UmsWorkCenterMapper;
import com.rh.i_mes_plus.model.ums.UmsWorkCenter;
import com.rh.i_mes_plus.service.ums.IUmsWorkCenterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 工作中心
 *
 * @author hbq
 * @date 2021-05-20 15:31:14
 */
@Slf4j
@Service
public class UmsWorkCenterServiceImpl extends ServiceImpl<UmsWorkCenterMapper, UmsWorkCenter> implements IUmsWorkCenterService {
    @Resource
    private UmsWorkCenterMapper umsWorkCenterMapper;
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
        return umsWorkCenterMapper.findList(pages, params);
    }
}
