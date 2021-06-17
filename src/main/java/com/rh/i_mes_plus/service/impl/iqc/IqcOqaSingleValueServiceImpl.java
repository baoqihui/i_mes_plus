package com.rh.i_mes_plus.service.impl.iqc;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.iqc.IqcOqaSingleValueMapper;
import com.rh.i_mes_plus.model.iqc.IqcOqaSingleValue;
import com.rh.i_mes_plus.service.iqc.IIqcOqaSingleValueService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 样品检测项目值
 *
 * @author hbq
 * @date 2020-10-22 16:28:19
 */
@Slf4j
@Service
public class IqcOqaSingleValueServiceImpl extends ServiceImpl<IqcOqaSingleValueMapper, IqcOqaSingleValue> implements IIqcOqaSingleValueService {
    @Resource
    private IqcOqaSingleValueMapper iqcOqaSingleValueMapper;
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
        return iqcOqaSingleValueMapper.findList(pages, params);
    }

    @Override
    public Integer countNg(String oqcNo, Long tiyId) {
        return iqcOqaSingleValueMapper.countNg(oqcNo,tiyId);
    }
}
