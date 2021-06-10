package com.rh.i_mes_plus.service.impl.gtoa;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.gtoa.WmsWorkTracingMainMapper;
import com.rh.i_mes_plus.model.gtoa.WmsWorkTracingMain;
import com.rh.i_mes_plus.service.gtoa.IWmsWorkTracingMainService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 
 *
 * @author hbq
 * @date 2020-12-02 15:59:56
 */
@Slf4j
@Service
public class WmsWorkTracingMainServiceImpl extends ServiceImpl<WmsWorkTracingMainMapper, WmsWorkTracingMain> implements IWmsWorkTracingMainService {
    @Resource
    private WmsWorkTracingMainMapper wmsWorkTracingMainMapper;
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
        return wmsWorkTracingMainMapper.findList(pages, params);
    }
}
