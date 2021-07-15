package com.rh.i_mes_plus.service.impl.sps;
import com.rh.i_mes_plus.mapper.sps.TinLogMapper;
import com.rh.i_mes_plus.model.sps.TinLog;
import com.rh.i_mes_plus.service.sps.ITinLogService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import lombok.extern.slf4j.Slf4j;


/**
 * 锡膏操作日志
 *
 * @author hbq
 * @date 2021-07-15 10:18:07
 */
@Slf4j
@Service
public class TinLogServiceImpl extends ServiceImpl<TinLogMapper, TinLog> implements ITinLogService {
    @Resource
    private TinLogMapper tinLogMapper;
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
        return tinLogMapper.findList(pages, params);
    }
}
