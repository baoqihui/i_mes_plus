package com.rh.i_mes_plus.service.impl.sps;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.sps.SpsFeederMaintenLogMapper;
import com.rh.i_mes_plus.model.sps.SpsFeederMaintenLog;
import com.rh.i_mes_plus.service.sps.ISpsFeederMaintenLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 设备保养维修管理
 *
 * @author hbq
 * @date 2021-06-08 17:55:16
 */
@Slf4j
@Service
public class SpsFeederMaintenLogServiceImpl extends ServiceImpl<SpsFeederMaintenLogMapper, SpsFeederMaintenLog> implements ISpsFeederMaintenLogService {
    @Resource
    private SpsFeederMaintenLogMapper spsFeederMaintenLogMapper;
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
        return spsFeederMaintenLogMapper.findList(pages, params);
    }
}
