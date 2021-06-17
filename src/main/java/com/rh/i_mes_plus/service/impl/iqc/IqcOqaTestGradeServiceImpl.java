package com.rh.i_mes_plus.service.impl.iqc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.iqc.IqcOqaTestGradeMapper;
import com.rh.i_mes_plus.model.iqc.IqcOqaTestGrade;
import com.rh.i_mes_plus.service.iqc.IIqcOqaTestGradeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 检验水平
 *
 * @author hqb
 * @date 2020-10-16 13:20:30
 */
@Slf4j
@Service
public class IqcOqaTestGradeServiceImpl extends ServiceImpl<IqcOqaTestGradeMapper, IqcOqaTestGrade> implements IIqcOqaTestGradeService {
    @Resource
    private IqcOqaTestGradeMapper iqcOqaTestGradeMapper;
    /**
     * 列表
     * @param params
     * @return
     */
    public Page<IqcOqaTestGrade> findList(Map<String, Object> params){
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<IqcOqaTestGrade> pages = new Page<>(pageNum, pageSize);
        return iqcOqaTestGradeMapper.findList(pages, params);
    }
}
