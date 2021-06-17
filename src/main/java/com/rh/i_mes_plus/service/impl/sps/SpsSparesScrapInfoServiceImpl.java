package com.rh.i_mes_plus.service.impl.sps;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.sps.SpsSparesScrapInfoMapper;
import com.rh.i_mes_plus.model.sps.SpsSparesScrapInfo;
import com.rh.i_mes_plus.service.sps.ISpsSparesScrapInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 报废信息表
 *
 * @author hbq
 * @date 2021-02-01 14:06:08
 */
@Slf4j
@Service
public class SpsSparesScrapInfoServiceImpl extends ServiceImpl<SpsSparesScrapInfoMapper, SpsSparesScrapInfo> implements ISpsSparesScrapInfoService {
    @Resource
    private SpsSparesScrapInfoMapper spsSparesScrapInfoMapper;
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
        return spsSparesScrapInfoMapper.findList(pages, params);
    }
}
