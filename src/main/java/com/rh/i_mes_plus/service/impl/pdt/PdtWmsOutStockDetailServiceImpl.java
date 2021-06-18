package com.rh.i_mes_plus.service.impl.pdt;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.pdt.PdtWmsOutStockDetailMapper;
import com.rh.i_mes_plus.model.pdt.PdtWmsOutStockDetail;
import com.rh.i_mes_plus.service.pdt.IPdtWmsOutStockDetailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 成品出库详情表
 *
 * @author hbq
 * @date 2021-01-06 15:22:23
 */
@Slf4j
@Service
public class PdtWmsOutStockDetailServiceImpl extends ServiceImpl<PdtWmsOutStockDetailMapper, PdtWmsOutStockDetail> implements IPdtWmsOutStockDetailService {
    @Resource
    private PdtWmsOutStockDetailMapper pdtWmsOutStockDetailMapper;
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
        return pdtWmsOutStockDetailMapper.findList(pages, params);
    }

    @Override
    public List<Map<String, Object>> getListByDocNo(Map<String, Object> map) {
        return pdtWmsOutStockDetailMapper.getListByDocNo(map);
    }
}



