package com.rh.i_mes_plus.service.impl.iqc;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.iqc.IqcOqaSingleMapper;
import com.rh.i_mes_plus.model.iqc.IqcOqaSingle;
import com.rh.i_mes_plus.service.iqc.IIqcOqaSingleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 抽验样本信息
 *
 * @author hbq
 * @date 2020-10-23 11:39:35
 */
@Slf4j
@Service
public class IqcOqaSingleServiceImpl extends ServiceImpl<IqcOqaSingleMapper, IqcOqaSingle> implements IIqcOqaSingleService {
    @Resource
    private IqcOqaSingleMapper iqcOqaSingleMapper;
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
        return iqcOqaSingleMapper.findList(pages, params);
    }
}
