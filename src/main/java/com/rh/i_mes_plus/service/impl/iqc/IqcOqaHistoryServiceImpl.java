package com.rh.i_mes_plus.service.impl.iqc;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.iqc.IqcOqaHistoryMapper;
import com.rh.i_mes_plus.model.iqc.IqcOqaHistory;
import com.rh.i_mes_plus.service.iqc.IIqcOqaHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * OQA抽检历史记录
 *
 * @author hbq
 * @date 2020-10-22 16:28:19
 */
@Slf4j
@Service
public class IqcOqaHistoryServiceImpl extends ServiceImpl<IqcOqaHistoryMapper, IqcOqaHistory> implements IIqcOqaHistoryService {
    @Resource
    private IqcOqaHistoryMapper iqcOqaHistoryMapper;
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
        return iqcOqaHistoryMapper.findList(pages, params);
    }
}
