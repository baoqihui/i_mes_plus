package com.rh.i_mes_plus.service.impl.iqc;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.iqc.IqcOqaBathMapper;
import com.rh.i_mes_plus.model.iqc.IqcOqaBath;
import com.rh.i_mes_plus.service.iqc.IIqcOqaBathService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 检验单明细
 *
 * @author hbq
 * @date 2020-10-21 14:29:19
 */
@Slf4j
@Service
public class IqcOqaBathServiceImpl extends ServiceImpl<IqcOqaBathMapper, IqcOqaBath> implements IIqcOqaBathService {
    @Resource
    private IqcOqaBathMapper iqcOqaBathMapper;
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
        return iqcOqaBathMapper.findList(pages, params);
    }
}
