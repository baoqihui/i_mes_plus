package com.rh.i_mes_plus.service.impl.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.ums.UmsCoItemsourceMapper;
import com.rh.i_mes_plus.model.ums.UmsCoItemsource;
import com.rh.i_mes_plus.service.ums.IUmsCoItemsourceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 物料类型
 *
 * @author hqb
 * @date 2020-09-21 16:35:09
 */
@Slf4j
@Service
public class UmsCoItemsourceServiceImpl extends ServiceImpl<UmsCoItemsourceMapper, UmsCoItemsource> implements IUmsCoItemsourceService {
    @Resource
    private UmsCoItemsourceMapper umsCoItemsourceMapper;
    /**
     * 列表
     * @param params
     * @return
     */
    public Page<UmsCoItemsource> findList(Map<String, Object> params){
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<UmsCoItemsource> pages = new Page<>(pageNum, pageSize);
        return umsCoItemsourceMapper.findList(pages, params);
    }
}
