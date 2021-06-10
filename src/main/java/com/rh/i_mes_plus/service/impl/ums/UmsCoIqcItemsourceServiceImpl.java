package com.rh.i_mes_plus.service.impl.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.ums.UmsCoIqcItemsourceMapper;
import com.rh.i_mes_plus.model.ums.UmsCoIqcItemsource;
import com.rh.i_mes_plus.service.ums.IUmsCoIqcItemsourceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * IQC检验分类表
 *
 * @author hqb
 * @date 2020-09-21 16:35:09
 */
@Slf4j
@Service
public class UmsCoIqcItemsourceServiceImpl extends ServiceImpl<UmsCoIqcItemsourceMapper, UmsCoIqcItemsource> implements IUmsCoIqcItemsourceService {
    @Resource
    private UmsCoIqcItemsourceMapper umsCoIqcItemsourceMapper;
    /**
     * 列表
     * @param params
     * @return
     */
    public Page<UmsCoIqcItemsource> findList(Map<String, Object> params){
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<UmsCoIqcItemsource> pages = new Page<>(pageNum, pageSize);
        return umsCoIqcItemsourceMapper.findList(pages, params);
    }
}
