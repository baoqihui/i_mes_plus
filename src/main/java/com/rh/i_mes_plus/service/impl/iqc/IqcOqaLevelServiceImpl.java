package com.rh.i_mes_plus.service.impl.iqc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.iqc.IqcOqaLevelMapper;
import com.rh.i_mes_plus.model.iqc.IqcOqaLevel;
import com.rh.i_mes_plus.service.iqc.IIqcOqaLevelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 检验水准
 *
 * @author hqb
 * @date 2020-10-16 10:06:17
 */
@Slf4j
@Service
public class IqcOqaLevelServiceImpl extends ServiceImpl<IqcOqaLevelMapper, IqcOqaLevel> implements IIqcOqaLevelService {
    @Resource
    private IqcOqaLevelMapper iqcOqaLevelMapper;
    /**
     * 列表
     * @param params
     * @return
     */
    public Page<IqcOqaLevel> findList(Map<String, Object> params){
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<IqcOqaLevel> pages = new Page<>(pageNum, pageSize);
        return iqcOqaLevelMapper.findList(pages, params);
    }
}
