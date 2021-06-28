package com.rh.i_mes_plus.service.impl.sps;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.sps.SpsSparesCheckDetailMapper;
import com.rh.i_mes_plus.model.sps.SpsSparesCheckDetail;
import com.rh.i_mes_plus.service.sps.ISpsSparesCheckDetailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 盘点明细表
 *
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
@Slf4j
@Service
public class SpsSparesCheckDetailServiceImpl extends ServiceImpl<SpsSparesCheckDetailMapper, SpsSparesCheckDetail> implements ISpsSparesCheckDetailService {
    @Resource
    private SpsSparesCheckDetailMapper spsSparesCheckDetailMapper;
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
        return spsSparesCheckDetailMapper.findList(pages, params);
    }

    @Override
    public List<Map<String, Object>> getGZBPDetailByCheckNo(String checkNo) {
        return spsSparesCheckDetailMapper.getGZBPDetailByCheckNo(checkNo);
    }

    @Override
    public List<Map<String, Object>> getGWDetailByCheckNo(String checkNo) {
        return spsSparesCheckDetailMapper.getGWDetailByCheckNo(checkNo);
    }

    @Override
    public List<Map<String, Object>> getLQDetailByCheckNo(String checkNo) {
        return spsSparesCheckDetailMapper.getLQDetailByCheckNo(checkNo);
    }

    @Override
    public List<Map<String, Object>> getDefaultDetailByCheckNo(String checkNo) {
        return spsSparesCheckDetailMapper.getDefaultDetailByCheckNo(checkNo);
    }
}
