package com.rh.i_mes_plus.service.impl.sps;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.sps.EngEquDetailInfoMapper;
import com.rh.i_mes_plus.model.sps.EngEquDetailInfo;
import com.rh.i_mes_plus.service.sps.IEngEquDetailInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 工程设备详情信息表
 *
 * @author hbq
 * @date 2021-02-24 18:51:21
 */
@Slf4j
@Service
public class EngEquDetailInfoServiceImpl extends ServiceImpl<EngEquDetailInfoMapper, EngEquDetailInfo> implements IEngEquDetailInfoService {
    @Resource
    private EngEquDetailInfoMapper engEquDetailInfoMapper;
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
        return engEquDetailInfoMapper.findList(pages, params);
    }
}
