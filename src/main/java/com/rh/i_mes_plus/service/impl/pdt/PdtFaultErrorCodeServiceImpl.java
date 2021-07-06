package com.rh.i_mes_plus.service.impl.pdt;
import com.rh.i_mes_plus.mapper.pdt.PdtFaultErrorCodeMapper;
import com.rh.i_mes_plus.service.pdt.IPdtFaultErrorCodeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import lombok.extern.slf4j.Slf4j;

import com.rh.i_mes_plus.model.pdt.PdtFaultErrorCode;

/**
 * 不良代码
 *
 * @author hbq
 * @date 2021-07-05 10:11:08
 */
@Slf4j
@Service
public class PdtFaultErrorCodeServiceImpl extends ServiceImpl<PdtFaultErrorCodeMapper, PdtFaultErrorCode> implements IPdtFaultErrorCodeService {
    @Resource
    private PdtFaultErrorCodeMapper pdtFaultErrorCodeMapper;
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
        return pdtFaultErrorCodeMapper.findList(pages, params);
    }
}
