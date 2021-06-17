package com.rh.i_mes_plus.service.impl.iqc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.iqc.IqcTestItemTypeMapper;
import com.rh.i_mes_plus.model.iqc.IqcTestItemType;
import com.rh.i_mes_plus.service.iqc.IIqcTestItemTypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 检测项目类型
 *
 * @author hqb
 * @date 2020-10-16 10:06:18
 */
@Slf4j
@Service
public class IqcTestItemTypeServiceImpl extends ServiceImpl<IqcTestItemTypeMapper, IqcTestItemType> implements IIqcTestItemTypeService {
    @Resource
    private IqcTestItemTypeMapper iqcTestItemTypeMapper;
    /**
     * 列表
     * @param params
     * @return
     */
    public Page<IqcTestItemType> findList(Map<String, Object> params){
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<IqcTestItemType> pages = new Page<>(pageNum, pageSize);
        return iqcTestItemTypeMapper.findList(pages, params);
    }
}
