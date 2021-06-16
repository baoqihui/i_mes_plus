package com.rh.i_mes_plus.service.impl.other;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.other.PdaMesLogMapper;
import com.rh.i_mes_plus.model.other.PdaMesLog;
import com.rh.i_mes_plus.service.other.IPdaMesLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * pda与mes操作日志
 *
 * @author hbq
 * @date 2021-01-28 17:56:25
 */
@Slf4j
@Service
public class PdaMesLogServiceImpl extends ServiceImpl<PdaMesLogMapper, PdaMesLog> implements IPdaMesLogService {
    @Resource
    private PdaMesLogMapper pdaMesLogMapper;
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
        return pdaMesLogMapper.findList(pages, params);
    }
}
