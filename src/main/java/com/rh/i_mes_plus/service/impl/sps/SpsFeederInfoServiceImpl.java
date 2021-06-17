package com.rh.i_mes_plus.service.impl.sps;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.sps.SpsFeederInfoMapper;
import com.rh.i_mes_plus.model.sps.SpsFeederInfo;
import com.rh.i_mes_plus.service.sps.ISpsFeederInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 料站表详情
 *
 * @author hbq
 * @date 2021-05-21 15:02:34
 */
@Slf4j
@Service
public class SpsFeederInfoServiceImpl extends ServiceImpl<SpsFeederInfoMapper, SpsFeederInfo> implements ISpsFeederInfoService {
    @Resource
    private SpsFeederInfoMapper spsFeederInfoMapper;
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
        return spsFeederInfoMapper.findList(pages, params);
    }
}
