package com.rh.i_mes_plus.service.impl.sps;
import com.rh.i_mes_plus.mapper.sps.TinUseRecordMapper;
import com.rh.i_mes_plus.service.sps.ITinUseRecordService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import lombok.extern.slf4j.Slf4j;

import com.rh.i_mes_plus.model.sps.TinUseRecord;

/**
 * 红锡膏领用记录
 *
 * @author hbq
 * @date 2021-07-08 19:48:01
 */
@Slf4j
@Service
public class TinUseRecordServiceImpl extends ServiceImpl<TinUseRecordMapper, TinUseRecord> implements ITinUseRecordService {
    @Resource
    private TinUseRecordMapper tinUseRecordMapper;
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
        return tinUseRecordMapper.findList(pages, params);
    }
}
