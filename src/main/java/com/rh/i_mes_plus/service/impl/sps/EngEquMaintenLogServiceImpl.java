package com.rh.i_mes_plus.service.impl.sps;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.sps.EngEquMaintenLogMapper;
import com.rh.i_mes_plus.model.sps.EngEquMaintenLog;
import com.rh.i_mes_plus.service.sps.IEngEquMaintenLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 设备保养维修管理
 *
 * @author hbq
 * @date 2021-06-08 15:32:04
 */
@Slf4j
@Service
public class EngEquMaintenLogServiceImpl extends ServiceImpl<EngEquMaintenLogMapper, EngEquMaintenLog> implements IEngEquMaintenLogService {
    @Resource
    private EngEquMaintenLogMapper engEquMaintenLogMapper;
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
        return engEquMaintenLogMapper.findList(pages, params);
    }
}
