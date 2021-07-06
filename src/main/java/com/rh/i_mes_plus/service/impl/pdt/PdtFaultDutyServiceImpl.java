package com.rh.i_mes_plus.service.impl.pdt;
import com.rh.i_mes_plus.mapper.pdt.PdtFaultDutyMapper;
import com.rh.i_mes_plus.service.pdt.IPdtFaultDutyService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import lombok.extern.slf4j.Slf4j;

import com.rh.i_mes_plus.model.pdt.PdtFaultDuty;

/**
 * 不良责任
 *
 * @author hbq
 * @date 2021-07-05 10:11:08
 */
@Slf4j
@Service
public class PdtFaultDutyServiceImpl extends ServiceImpl<PdtFaultDutyMapper, PdtFaultDuty> implements IPdtFaultDutyService {
    @Resource
    private PdtFaultDutyMapper pdtFaultDutyMapper;
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
        return pdtFaultDutyMapper.findList(pages, params);
    }
}
