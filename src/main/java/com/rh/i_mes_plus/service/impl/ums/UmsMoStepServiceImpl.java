package com.rh.i_mes_plus.service.impl.ums;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.ums.UmsMoStepMapper;
import com.rh.i_mes_plus.model.ums.UmsMoStep;
import com.rh.i_mes_plus.service.ums.IUmsMoStepService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 工单阶别(0-SMT,1-WAVE,2-Hand Soldering,3-Assembly,4-Others)
 *
 * @author hbq
 * @date 2021-05-31 10:07:36
 */
@Slf4j
@Service
public class UmsMoStepServiceImpl extends ServiceImpl<UmsMoStepMapper, UmsMoStep> implements IUmsMoStepService {
    @Resource
    private UmsMoStepMapper umsMoStepMapper;
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
        return umsMoStepMapper.findList(pages, params);
    }
}
