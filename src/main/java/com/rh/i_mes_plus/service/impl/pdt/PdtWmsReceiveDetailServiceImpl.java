package com.rh.i_mes_plus.service.impl.pdt;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.pdt.PdtWmsReceiveDetailMapper;
import com.rh.i_mes_plus.model.pdt.PdtWmsReceiveDetail;
import com.rh.i_mes_plus.service.pdt.IPdtWmsReceiveDetailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 入库单明细表
 *
 * @author hbq
 * @date 2020-12-29 15:41:49
 */
@Slf4j
@Service
public class PdtWmsReceiveDetailServiceImpl extends ServiceImpl<PdtWmsReceiveDetailMapper, PdtWmsReceiveDetail> implements IPdtWmsReceiveDetailService {
    @Resource
    private PdtWmsReceiveDetailMapper pdtWmsReceiveDetailMapper;
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
        return pdtWmsReceiveDetailMapper.findList(pages, params);
    }

    @Override
    public List<Map<String, Object>> listAll(String docNo) {
        return pdtWmsReceiveDetailMapper.listAll(docNo);
    }
}
