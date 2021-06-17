package com.rh.i_mes_plus.service.impl.sps;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.sps.GzkPartsDetailInfoMapper;
import com.rh.i_mes_plus.model.sps.GzkPartsDetailInfo;
import com.rh.i_mes_plus.service.sps.IGzkPartsDetailInfoService;
import com.rh.i_mes_plus.vo.GzkPartsDetailInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 工装备品详情信息表
 *
 * @author hbq
 * @date 2021-02-21 10:39:50
 */
@Slf4j
@Service
public class GzkPartsDetailInfoServiceImpl extends ServiceImpl<GzkPartsDetailInfoMapper, GzkPartsDetailInfo> implements IGzkPartsDetailInfoService {
    @Resource
    private GzkPartsDetailInfoMapper gzkPartsDetailInfoMapper;
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
        return gzkPartsDetailInfoMapper.findList(pages, params);
    }

    @Override
    public Page<Map> statement(Map<String, Object> params) {
        Integer pageNum = MapUtils.getInteger(params, "pageNum");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = -1;
        }
        Page<Map> pages = new Page<>(pageNum, pageSize);
        return gzkPartsDetailInfoMapper.statement(pages, params);
    }

    @Override
    public List<GzkPartsDetailInfoVO> leadOutByIds(List<Long> ids) {
        return gzkPartsDetailInfoMapper.leadOutByIds(ids);
    }
}
