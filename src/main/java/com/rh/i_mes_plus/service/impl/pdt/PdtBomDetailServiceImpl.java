package com.rh.i_mes_plus.service.impl.pdt;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.pdt.PdtBomDetailMapper;
import com.rh.i_mes_plus.model.pdt.PdtBomDetail;
import com.rh.i_mes_plus.service.pdt.IPdtBomDetailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 
 *
 * @author hbq
 * @date 2021-05-25 13:18:22
 */
@Slf4j
@Service
public class PdtBomDetailServiceImpl extends ServiceImpl<PdtBomDetailMapper, PdtBomDetail> implements IPdtBomDetailService {
    @Resource
    private PdtBomDetailMapper pdtBomDetailMapper;
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
        return pdtBomDetailMapper.findList(pages, params);
    }
}
