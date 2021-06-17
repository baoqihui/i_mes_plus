package com.rh.i_mes_plus.service.impl.iqc;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.iqc.IqcOqaTestLevelMapper;
import com.rh.i_mes_plus.model.iqc.IqcOqaTestLevel;
import com.rh.i_mes_plus.service.iqc.IIqcOqaTestLevelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 机种抽样方案
 *
 * @author hqb
 * @date 2020-10-16 11:25:37
 */
@Slf4j
@Service
public class IqcOqaTestLevelServiceImpl extends ServiceImpl<IqcOqaTestLevelMapper, IqcOqaTestLevel> implements IIqcOqaTestLevelService {
    @Resource
    private IqcOqaTestLevelMapper iqcOqaTestLevelMapper;
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
        return iqcOqaTestLevelMapper.findList(pages, params);
    }

    @Override
    public Integer getModelAndLevel(String itemCode) {
        return iqcOqaTestLevelMapper.getModelAndLevel(itemCode);
    }
}
