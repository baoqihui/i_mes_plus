package com.rh.i_mes_plus.service.impl.iqc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.iqc.IqcTestItemModelMapper;
import com.rh.i_mes_plus.model.iqc.IqcTestItemModel;
import com.rh.i_mes_plus.service.iqc.IIqcTestItemModelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 机种检验项目
 *
 * @author hqb
 * @date 2020-10-16 16:35:54
 */
@Slf4j
@Service
public class IqcTestItemModelServiceImpl extends ServiceImpl<IqcTestItemModelMapper, IqcTestItemModel> implements IIqcTestItemModelService {
    @Resource
    private IqcTestItemModelMapper iqcTestItemModelMapper;
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
        return iqcTestItemModelMapper.findList(pages, params);
    }
}
