package com.rh.i_mes_plus.service.impl.ums;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.ums.UmsTechnicsMapper;
import com.rh.i_mes_plus.model.ums.UmsTechnics;
import com.rh.i_mes_plus.service.ums.IUmsTechnicsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 工艺
 *
 * @author hbq
 * @date 2021-06-01 10:57:04
 */
@Slf4j
@Service
public class UmsTechnicsServiceImpl extends ServiceImpl<UmsTechnicsMapper, UmsTechnics> implements IUmsTechnicsService {
    @Resource
    private UmsTechnicsMapper umsTechnicsMapper;
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
        return umsTechnicsMapper.findList(pages, params);
    }

    @Override
    public Map<String, Object> isPcbUnpack(Long id) {
        return umsTechnicsMapper.isPcbUnpack(id);
    }
}
