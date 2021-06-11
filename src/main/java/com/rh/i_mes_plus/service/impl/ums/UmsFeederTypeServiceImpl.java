package com.rh.i_mes_plus.service.impl.ums;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.ums.UmsFeederTypeMapper;
import com.rh.i_mes_plus.model.ums.UmsFeederType;
import com.rh.i_mes_plus.service.ums.IUmsFeederTypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 料枪类型
 *
 * @author hbq
 * @date 2021-05-21 15:02:34
 */
@Slf4j
@Service
public class UmsFeederTypeServiceImpl extends ServiceImpl<UmsFeederTypeMapper, UmsFeederType> implements IUmsFeederTypeService {
    @Resource
    private UmsFeederTypeMapper umsFeederTypeMapper;
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
        return umsFeederTypeMapper.findList(pages, params);
    }
}
