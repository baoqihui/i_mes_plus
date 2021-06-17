package com.rh.i_mes_plus.service.impl.iqc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.iqc.IqcOqaTestTypeMapper;
import com.rh.i_mes_plus.model.iqc.IqcOqaTestType;
import com.rh.i_mes_plus.service.iqc.IIqcOqaTestTypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 抽样方案
(正常、加严、放宽)
 *
 * @author hqb
 * @date 2020-10-16 10:06:17
 */
@Slf4j
@Service
public class IqcOqaTestTypeServiceImpl extends ServiceImpl<IqcOqaTestTypeMapper, IqcOqaTestType> implements IIqcOqaTestTypeService {
    @Resource
    private IqcOqaTestTypeMapper iqcOqaTestTypeMapper;
    /**
     * 列表
     * @param params
     * @return
     */
    public Page<IqcOqaTestType> findList(Map<String, Object> params){
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<IqcOqaTestType> pages = new Page<>(pageNum, pageSize);
        return iqcOqaTestTypeMapper.findList(pages, params);
    }
}
