package com.rh.i_mes_plus.service.impl.gtoa;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.gtoa.EcnDetailAuditLogMapper;
import com.rh.i_mes_plus.model.gtoa.EcnDetailAuditLog;
import com.rh.i_mes_plus.service.gtoa.IEcnDetailAuditLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * ECN详情表
 *
 * @author hbq
 * @date 2020-10-26 18:58:29
 */
@Slf4j
@Service
public class EcnDetailAuditLogServiceImpl extends ServiceImpl<EcnDetailAuditLogMapper, EcnDetailAuditLog> implements IEcnDetailAuditLogService {
    @Resource
    private EcnDetailAuditLogMapper ecnDetailAuditLogMapper;
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
        return ecnDetailAuditLogMapper.findList(pages, params);
    }
}
