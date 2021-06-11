package com.rh.i_mes_plus.service.impl.ums;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.ums.UmsFeederSpecMapper;
import com.rh.i_mes_plus.model.ums.UmsFeederSpec;
import com.rh.i_mes_plus.service.ums.IUmsFeederSpecService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 料枪规格
 *
 * @author hbq
 * @date 2021-05-21 15:02:34
 */
@Slf4j
@Service
public class UmsFeederSpecServiceImpl extends ServiceImpl<UmsFeederSpecMapper, UmsFeederSpec> implements IUmsFeederSpecService {
    @Resource
    private UmsFeederSpecMapper umsFeederSpecMapper;
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
        return umsFeederSpecMapper.findList(pages, params);
    }
}
