package com.rh.i_mes_plus.service.impl.ums;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.ums.UmsTechnicsPathMapper;
import com.rh.i_mes_plus.model.ums.UmsTechnicsPath;
import com.rh.i_mes_plus.service.ums.IUmsTechnicsPathService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 工艺路线
 *
 * @author hbq
 * @date 2021-06-01 10:57:04
 */
@Slf4j
@Service
public class UmsTechnicsPathServiceImpl extends ServiceImpl<UmsTechnicsPathMapper, UmsTechnicsPath> implements IUmsTechnicsPathService {
    @Resource
    private UmsTechnicsPathMapper umsTechnicsPathMapper;
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
        return umsTechnicsPathMapper.findList(pages, params);
    }
}
