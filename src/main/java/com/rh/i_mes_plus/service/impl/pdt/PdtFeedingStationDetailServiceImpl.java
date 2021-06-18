package com.rh.i_mes_plus.service.impl.pdt;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.pdt.PdtFeedingStationDetailMapper;
import com.rh.i_mes_plus.model.pdt.PdtFeedingStationDetail;
import com.rh.i_mes_plus.service.pdt.IPdtFeedingStationDetailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 料站表详情
 *
 * @author hbq
 * @date 2021-05-24 10:17:57
 */
@Slf4j
@Service
public class PdtFeedingStationDetailServiceImpl extends ServiceImpl<PdtFeedingStationDetailMapper, PdtFeedingStationDetail> implements IPdtFeedingStationDetailService {
    @Resource
    private PdtFeedingStationDetailMapper pdtFeedingStationDetailMapper;
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
        return pdtFeedingStationDetailMapper.findList(pages, params);
    }
}
