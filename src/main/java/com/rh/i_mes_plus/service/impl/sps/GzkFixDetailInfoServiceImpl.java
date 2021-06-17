package com.rh.i_mes_plus.service.impl.sps;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.sps.GzkFixDetailInfoMapper;
import com.rh.i_mes_plus.model.sps.GzkFixDetailInfo;
import com.rh.i_mes_plus.service.sps.IGzkFixDetailInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 工装治具
 *
 * @author hbq
 * @date 2021-02-23 10:06:16
 */
@Slf4j
@Service
public class GzkFixDetailInfoServiceImpl extends ServiceImpl<GzkFixDetailInfoMapper, GzkFixDetailInfo> implements IGzkFixDetailInfoService {
    @Resource
    private GzkFixDetailInfoMapper gzkFixDetailInfoMapper;
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
        return gzkFixDetailInfoMapper.findList(pages, params);
    }

    @Override
    public List<Map> leadOut(List<Long> ids) {
        return gzkFixDetailInfoMapper.leadOut(ids);
    }
}
