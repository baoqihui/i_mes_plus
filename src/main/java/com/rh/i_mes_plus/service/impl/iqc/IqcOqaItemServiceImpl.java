package com.rh.i_mes_plus.service.impl.iqc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.iqc.IqcOqaItemMapper;
import com.rh.i_mes_plus.model.iqc.IqcOqaItem;
import com.rh.i_mes_plus.service.iqc.IIqcOqaItemService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 抽样项目
 *
 * @author hbq
 * @date 2020-10-22 16:28:19
 */
@Slf4j
@Service
public class IqcOqaItemServiceImpl extends ServiceImpl<IqcOqaItemMapper, IqcOqaItem> implements IIqcOqaItemService {
    @Resource
    private IqcOqaItemMapper iqcOqaItemMapper;
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
        return iqcOqaItemMapper.findList(pages, params);
    }
}
