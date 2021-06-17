package com.rh.i_mes_plus.service.impl.iqc;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.iqc.IqcCoQcSamplesMapper;
import com.rh.i_mes_plus.model.iqc.IqcCoQcSamples;
import com.rh.i_mes_plus.service.iqc.IIqcCoQcSamplesService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 样本代码
 *
 * @author hbq
 * @date 2020-10-23 15:24:30
 */
@Slf4j
@Service
public class IqcCoQcSamplesServiceImpl extends ServiceImpl<IqcCoQcSamplesMapper, IqcCoQcSamples> implements IIqcCoQcSamplesService {
    @Resource
    private IqcCoQcSamplesMapper iqcCoQcSamplesMapper;
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
        return iqcCoQcSamplesMapper.findList(pages, params);
    }

    @Override
    public String getCodeBySendAmount(Long oqcSendAmount,Long otgId) {
        return iqcCoQcSamplesMapper.getCodeBySendAmount(oqcSendAmount,otgId);
    }

    @Override
    public List<Map<String, Object>> selList(Map<String, Object> params) {
        return iqcCoQcSamplesMapper.selList(params);
    }
}
