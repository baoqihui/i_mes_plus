package com.rh.i_mes_plus.service.impl.iqc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.iqc.IqcOqaTestStepMapper;
import com.rh.i_mes_plus.model.iqc.IqcOqaTestStep;
import com.rh.i_mes_plus.service.iqc.IIqcOqaTestStepService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 检验阶
 *
 * @author hqb
 * @date 2020-10-16 10:06:17
 */
@Slf4j
@Service
public class IqcOqaTestStepServiceImpl extends ServiceImpl<IqcOqaTestStepMapper, IqcOqaTestStep> implements IIqcOqaTestStepService {
    @Resource
    private IqcOqaTestStepMapper iqcOqaTestStepMapper;
    /**
     * 列表
     * @param params
     * @return
     */
    public Page<IqcOqaTestStep> findList(Map<String, Object> params){
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<IqcOqaTestStep> pages = new Page<>(pageNum, pageSize);
        return iqcOqaTestStepMapper.findList(pages, params);
    }
}
