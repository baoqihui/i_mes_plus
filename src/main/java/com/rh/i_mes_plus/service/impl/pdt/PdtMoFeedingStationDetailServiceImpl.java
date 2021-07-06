package com.rh.i_mes_plus.service.impl.pdt;
import com.rh.i_mes_plus.mapper.pdt.PdtMoFeedingStationDetailMapper;
import com.rh.i_mes_plus.service.pdt.IPdtMoFeedingStationDetailService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import lombok.extern.slf4j.Slf4j;

import com.rh.i_mes_plus.model.pdt.PdtMoFeedingStationDetail;

/**
 * 
 *
 * @author hbq
 * @date 2021-07-06 13:52:46
 */
@Slf4j
@Service
public class PdtMoFeedingStationDetailServiceImpl extends ServiceImpl<PdtMoFeedingStationDetailMapper, PdtMoFeedingStationDetail> implements IPdtMoFeedingStationDetailService {
    @Resource
    private PdtMoFeedingStationDetailMapper pdtMoFeedingStationDetailMapper;
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
        return pdtMoFeedingStationDetailMapper.findList(pages, params);
    }
}
