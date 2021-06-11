package com.rh.i_mes_plus.service.impl.other;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.other.WmsProjectBaseMapper;
import com.rh.i_mes_plus.model.other.WmsProjectBase;
import com.rh.i_mes_plus.service.other.IWmsProjectBaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 工单信息主表
 *
 * @author hbq
 * @date 2021-05-27 08:41:55
 */
@Slf4j
@Service
public class WmsProjectBaseServiceImpl extends ServiceImpl<WmsProjectBaseMapper, WmsProjectBase> implements IWmsProjectBaseService {
    @Resource
    private WmsProjectBaseMapper wmsProjectBaseMapper;
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
        return wmsProjectBaseMapper.findList(pages, params);
    }
}
