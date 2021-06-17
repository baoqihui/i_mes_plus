package com.rh.i_mes_plus.service.impl.sps;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rh.i_mes_plus.mapper.sps.SpsEngFixReqDetailInfoMapper;
import com.rh.i_mes_plus.model.sps.SpsEngFixReqDetailInfo;
import com.rh.i_mes_plus.service.sps.ISpsEngFixReqDetailInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 工程治具借出详情表
 *
 * @author hbq
 * @date 2021-02-20 09:14:10
 */
@Slf4j
@Service
public class SpsEngFixReqDetailInfoServiceImpl extends ServiceImpl<SpsEngFixReqDetailInfoMapper, SpsEngFixReqDetailInfo> implements ISpsEngFixReqDetailInfoService {
    @Resource
    private SpsEngFixReqDetailInfoMapper spsEngFixReqDetailInfoMapper;
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
        return spsEngFixReqDetailInfoMapper.findList(pages, params);
    }

    @Override
    public List<Map<String, Object>> getReqDetail(String reqNo) {
        return spsEngFixReqDetailInfoMapper.getReqDetail(reqNo);
    }
}
