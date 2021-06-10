package com.rh.i_mes_plus.service.impl.other;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.other.SapMesLogMapper;
import com.rh.i_mes_plus.model.other.SapMesLog;
import com.rh.i_mes_plus.service.other.ISapMesLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * sap-mes接口错误日志
 *
 * @author hbq
 * @date 2020-10-31 10:58:31
 */
@Slf4j
@Service
public class SapMesLogServiceImpl extends ServiceImpl<SapMesLogMapper, SapMesLog> implements ISapMesLogService {
    @Resource
    private SapMesLogMapper sapMesLogMapper;
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
        return sapMesLogMapper.findList(pages, params);
    }
}
